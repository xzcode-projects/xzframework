package org.xzframework.integration.support.locks;

import java.io.Serial;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * Local in-memory {@link LockRegistry} implementation backed by a
 * {@link ConcurrentHashMap} with {@link WeakReference weak references} to the locks.
 * <p>
 * Correctness relies on JVM reachability semantics: as long as any thread strongly
 * references the lock obtained for a key (from {@link #obtain} until it is no longer
 * used), every subsequent {@link #obtain} call for that key is guaranteed to return
 * the same instance, providing strict mutual exclusion per key. Once no thread
 * references a lock anymore, it becomes eligible for garbage collection and the
 * corresponding registry entry is expunged lazily on the next {@link #obtain} call,
 * so the registry does not grow unboundedly with the number of distinct keys.
 * <p>
 * Unlike reference-counting approaches, this implementation imposes no pairing
 * discipline on callers: abandoned {@code tryLock} attempts never leak entries.
 * <p>
 * <b>Usage contract:</b> callers must retain a strong reference to the returned
 * lock until it is fully released, i.e. the reference obtained from
 * {@link #obtain} must be the very same instance passed to {@code unlock()}:
 * <pre>{@code
 * Lock lock = registry.obtain(key);
 * lock.lock();
 * try {
 *     // critical section
 * } finally {
 *     lock.unlock();
 * }
 * }</pre>
 * Holding a lock does <em>not</em> by itself keep the lock reachable for
 * garbage collection: the lock-to-thread association inside {@code ReentrantLock}
 * points from the lock to the owning thread, not the other way around. Chaining
 * calls such as {@code registry.obtain(key).lock()} followed by a fresh
 * {@code obtain} in the {@code finally} block is therefore unsafe: the original
 * lock may be collected in between and the second {@code obtain} would return a
 * new, different instance. Use {@link #withLock(Object, Runnable)} or
 * {@link #tryWithLock(Object, long, TimeUnit, Runnable)} which encapsulate the
 * safe pattern and cannot be misused.
 */
public class MapLockRegistry implements LockRegistry<Lock> {

    private final ConcurrentHashMap<Object, LockReference> locks = new ConcurrentHashMap<>();

    private final ReferenceQueue<ReentrantLock> queue = new ReferenceQueue<>();

    private final boolean fair;

    public MapLockRegistry() {
        this(false);
    }

    /**
     * Create a registry whose locks use the given fairness policy.
     *
     * @param fair {@code true} if the locks should be fair (see {@link ReentrantLock#ReentrantLock(boolean)})
     */
    public MapLockRegistry(boolean fair) {
        this.fair = fair;
    }

    @Override
    public Lock obtain(Object lockKey) {
        return obtainReentrant(lockKey);
    }

    /**
     * Run the given action while holding the lock for the given key. This method
     * encapsulates the safe usage pattern and guarantees the lock is unlocked
     * exactly once, on the same instance that was locked, even if the action
     * throws.
     *
     * @param lockKey the object with which the lock is associated
     * @param action the action to run under the lock
     */
    public void withLock(Object lockKey, Runnable action) {
        ReentrantLock lock = obtainReentrant(lockKey);
        lock.lock();
        try {
            action.run();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Run the given action while holding the lock for the given key and return
     * its result. See {@link #withLock(Object, Runnable)} for the guarantees.
     *
     * @param lockKey the object with which the lock is associated
     * @param action the action to run under the lock
     * @param <T> the result type
     * @return the value produced by the action
     */
    public <T> T withLock(Object lockKey, Supplier<T> action) {
        ReentrantLock lock = obtainReentrant(lockKey);
        lock.lock();
        try {
            return action.get();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Run the given action under the lock for the given key, waiting up to the
     * given timeout to acquire it. See {@link #withLock(Object, Runnable)} for
     * the guarantees.
     *
     * @param lockKey the object with which the lock is associated
     * @param timeout the maximum time to wait for the lock
     * @param unit the time unit of the timeout argument
     * @param action the action to run under the lock
     * @return {@code true} if the lock was acquired and the action ran;
     *         {@code false} if the waiting time elapsed before the lock was
     *         acquired
     * @throws InterruptedException if the current thread is interrupted while
     *         waiting for the lock
     */
    public boolean tryWithLock(Object lockKey, long timeout, TimeUnit unit, Runnable action)
            throws InterruptedException {
        ReentrantLock lock = obtainReentrant(lockKey);
        if (!lock.tryLock(timeout, unit)) {
            return false;
        }
        try {
            action.run();
            return true;
        } finally {
            lock.unlock();
        }
    }

    private ReentrantLock obtainReentrant(Object lockKey) {
        expungeStaleEntries();
        while (true) {
            LockReference ref = locks.computeIfAbsent(lockKey,
                    key -> new LockReference(new InnerLock(this.fair), this.queue, key));
            ReentrantLock lock = ref.get();
            if (lock != null) {
                return lock;
            }
            // The lock instance has been garbage-collected; remove the stale
            // entry and retry so that callers always receive a live instance.
            locks.remove(lockKey, ref);
        }
    }

    /**
     * Remove registry entries whose locks have already been garbage-collected.
     */
    private void expungeStaleEntries() {
        Reference<? extends ReentrantLock> ref;
        while ((ref = queue.poll()) != null) {
            LockReference stale = (LockReference) ref;
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
