package org.xzframework.integration.support.locks;

import java.io.Serial;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WeakLockRegistry implements LockRegistry<Lock> {


    private final ConcurrentHashMap<Object, LockReference> locks = new ConcurrentHashMap<>();

    private final ReferenceQueue<ReentrantLock> queue = new ReferenceQueue<>();

    private final boolean fair;

    public WeakLockRegistry() {
        this(false);
    }

    /**
     * 创建一个指定公平策略的锁注册表。
     *
     * @param fair 为 {@code true} 时使用公平锁 (参见 {@link ReentrantLock#ReentrantLock(boolean)})
     */
    public WeakLockRegistry(boolean fair) {
        this.fair = fair;
    }

    @Override
    public Lock obtain(Object lockKey) {
        // 1. 每次获取锁时，顺便惰性清理已经无线程使用的、被 GC 标记的过时 Entry
        expungeStaleEntries();

        // 2. 核心修复：通过 compute 的独占原子块确保线程安全
        LockReference activeRef = locks.compute(lockKey, (key, existingRef) -> {
            if (existingRef != null) {
                Lock lock = existingRef.get();
                if (lock != null) {
                    // 旧锁依然健在且被外界强引用着，直接沿用
                    return existingRef;
                }
            }
            // 旧锁不存在或已被 GC 释放，原子性地创建新锁并完成覆盖
            return new LockReference(new InnerLock(this.fair), this.queue, key);
        });

        // 3. 此时计算出的 activeRef 中持有的 Lock 一定存活
        return activeRef.get();
    }

    /**
     * 移出已被垃圾回收器回收的锁在 Map 中的 Entry
     */
    private void expungeStaleEntries() {
        Reference<? extends ReentrantLock> ref;
        while ((ref = queue.poll()) != null) {
            LockReference stale = (LockReference) ref;
            // 只有当 Map 中当前的 reference 确实是队列里这个过时的 reference 时才移除
            locks.remove(stale.lockKey, stale);
        }
    }

    private static final class InnerLock extends ReentrantLock {
        @Serial
        private static final long serialVersionUID = -733265318250367868L;

        private InnerLock(boolean fair) {
            super(fair);
        }
    }

    private static final class LockReference extends WeakReference<ReentrantLock> {
        private final Object lockKey;

        private LockReference(ReentrantLock lock, ReferenceQueue<? super ReentrantLock> queue, Object lockKey) {
            super(lock, queue);
            this.lockKey = lockKey;
        }
    }
}
