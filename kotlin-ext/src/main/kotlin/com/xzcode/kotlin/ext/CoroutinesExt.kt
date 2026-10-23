package com.xzcode.kotlin.ext

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.Semaphore

private const val DEFAULT_CONCURRENCY = 16

/**
 * 执行器工厂，进程内只解析一次：
 * JDK 21+ 返回虚拟线程执行器工厂；JDK 17~20 返回固定大小平台线程池工厂。
 */
private val newExecutor: (Int) -> ExecutorService by lazy {
    val method = try {
        Executors::class.java.getMethod("newVirtualThreadPerTaskExecutor")
    } catch (_: ReflectiveOperationException) {
        null
    }
    if (method != null) {
        { method.invoke(null) as ExecutorService }
    } else {
        { Executors.newFixedThreadPool(it) }
    }
}

/**
 * 以受限于 [concurrency] 的方式并发执行所有任务并等待完成。
 * JDK 21+ 使用虚拟线程执行器并通过信号量限制并发；
 * JDK 17~20 退回固定大小平台线程池，线程池本身即为并发上限。
 * 通过反射调用 newVirtualThreadPerTaskExecutor，避免对 JDK 21 API 的编译期依赖；
 * 方法查找结果由 [newExecutor] 在进程内缓存，反射开销可忽略。
 * ExecutorService 在 JDK 19 之前不是 AutoCloseable，因此需手动 shutdown。
 */
private fun <R> List<() -> R>.invokeAllBounded(concurrency: Int): List<Future<R>> {
    val executor = newExecutor(concurrency)
    val semaphore = Semaphore(concurrency)
    return try {
        executor.invokeAll(map { task ->
            Callable {
                semaphore.acquire()
                try {
                    task()
                } finally {
                    semaphore.release()
                }
            }
        })
    } finally {
        executor.shutdown()
    }
}

/**
 * 并发映射集合元素，[concurrency] 限制同时执行的任务数。
 * JDK 21+ 使用虚拟线程，低版本退回平台线程池。
 * 任务异常以 ExecutionException 形式从调用处抛出。
 */
fun <I, R> Collection<I>.pmap(
    concurrency: Int = DEFAULT_CONCURRENCY,
    f: (I) -> R
): Collection<R> {
    require(concurrency > 0) { "concurrency must be positive" }
    if (isEmpty()) return emptyList()
    return map<I, () -> R> { item -> { f(item) } }
        .invokeAllBounded(concurrency)
        .map { it.get() }
}

/**
 * 并发遍历集合元素，[concurrency] 限制同时执行的任务数。
 * JDK 21+ 使用虚拟线程，低版本退回平台线程池。
 * 任务异常以 ExecutionException 形式从调用处抛出。
 */
fun <I> Collection<I>.peach(
    concurrency: Int = DEFAULT_CONCURRENCY,
    f: (I) -> Unit
): Collection<I> {
    require(concurrency > 0) { "concurrency must be positive" }
    if (isEmpty()) return this
    map<I, () -> Unit> { item -> { f(item) } }
        .invokeAllBounded(concurrency)
        .forEach { it.get() }
    return this
}
