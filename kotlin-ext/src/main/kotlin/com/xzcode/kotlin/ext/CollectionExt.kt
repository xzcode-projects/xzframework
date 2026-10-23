package com.xzcode.kotlin.ext

/**
 * 计算两个集合的笛卡尔积
 */
fun <T, S, V> Collection<T>.cartesianProduct(other: Iterable<S>, transformer: (first: T, second: S) -> V): List<V> {
    return this.flatMap { first -> other.map { second -> transformer(first, second) } }
}

inline fun <E, T : Collection<E>> T?.ifNotEmpty(block: (T) -> Unit): T? {
    if (!isNullOrEmpty()) {
        block(this)
    }
    return this
}


/**
 * 清空当前集合并装入 [elements] 中的全部元素；[elements] 为 null 时不做任何操作。
 * 返回集合是否发生变化。
 */
fun <E> MutableCollection<E>.replace(elements: Collection<E>?): Boolean {
    return elements?.let {
        clear()
        addAll(it)
    } ?: false
}

inline fun <E> MutableCollection<E>.replace(elements: () -> Collection<E>?): Boolean {
    return elements()?.let {
        clear()
        addAll(it)
    } ?: false
}
