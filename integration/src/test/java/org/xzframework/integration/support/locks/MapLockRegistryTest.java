package org.xzframework.integration.support.locks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapLockRegistryTest {

    @Test
    @Timeout(30)
    void sameInstanceWhileReferenced() throws Exception {
        MapLockRegistry registry = new MapLockRegistry();
        Object key = new Object();

        // Each thread holds a strong reference to the returned lock for the
        // whole test; all obtains must therefore return the very same instance.
        Lock first = registry.obtain(key);
        ExecutorService pool = Executors.newFixedThreadPool(8);
        try {
            List<Future<Boolean>> results = new java.util.ArrayList<>();
            for (int i = 0; i < 32; i++) {
                results.add(pool.submit(() -> registry.obtain(key) == first));
            }
            for (Future<Boolean> result : results) {
                assertTrue(result.get(), "all concurrent obtains must return the same instance");
            }
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    @Timeout(30)
    void newInstanceAfterGarbageCollection() throws Exception {
        MapLockRegistry registry = new MapLockRegistry();
        Object key = new Object();

        // Drop the only strong reference; after GC the registry must evict the
        // stale entry and hand out a fresh instance for the same key.
        Lock before = registry.obtain(key);
        int identityBefore = System.identityHashCode(before);
        before = null;

        boolean evicted = false;
        for (int i = 0; i < 50 && !evicted; i++) {
            System.gc();
            TimeUnit.MILLISECONDS.sleep(20);
            Lock current = registry.obtain(key);
            evicted = System.identityHashCode(current) != identityBefore;
            current = null;
        }
        assertTrue(evicted, "registry must evict the entry after the lock becomes unreachable");
    }

    @Test
    @Timeout(30)
    void withLockMutualExclusion() throws Exception {
        MapLockRegistry registry = new MapLockRegistry();
        Object key = "biz-key";

        AtomicInteger inCritical = new AtomicInteger();
        AtomicInteger maxConcurrent = new AtomicInteger();
        AtomicInteger total = new AtomicInteger();
        int threads = 16;
        int iterations = 200;

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            CountDownLatch start = new CountDownLatch(1);
            List<Future<?>> futures = new java.util.ArrayList<>();
            for (int t = 0; t < threads; t++) {
                futures.add(pool.submit((Runnable) () -> {
                    try {
                        start.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    for (int i = 0; i < iterations; i++) {
                        registry.withLock(key, () -> {
                            int now = inCritical.incrementAndGet();
                            maxConcurrent.accumulateAndGet(now, Math::max);
                            total.incrementAndGet();
                            inCritical.decrementAndGet();
                        });
                    }
                }));
            }
            start.countDown();
            for (Future<?> future : futures) {
                future.get();
            }
        } finally {
            pool.shutdownNow();
        }

        assertEquals(threads * iterations, total.get());
        assertEquals(1, maxConcurrent.get(), "critical section must never be entered concurrently");
    }

    @Test
    @Timeout(30)
    void withLockSupplierReturnsValueAndUnlocks() {
        MapLockRegistry registry = new MapLockRegistry();
        Object key = new Object();

        String value = registry.withLock(key, () -> "computed");
        assertEquals("computed", value);

        // The lock was fully released, so another withLock must proceed.
        Lock lock = registry.obtain(key);
        assertTrue(lock.tryLock());
        lock.unlock();
    }

    @Test
    @Timeout(30)
    void tryWithLockTimesOut() throws Exception {
        MapLockRegistry registry = new MapLockRegistry();
        Object key = new Object();

        // Hold the lock on a separate thread so that the reentrant acquire
        // attempt from the test thread below cannot succeed immediately.
        CountDownLatch locked = new CountDownLatch(1);
        CountDownLatch release = new CountDownLatch(1);
        Thread holder = new Thread(() -> registry.withLock(key, () -> {
            locked.countDown();
            try {
                release.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
        holder.start();
        locked.await();

        try {
            boolean ran = registry.tryWithLock(key, 50, TimeUnit.MILLISECONDS, () -> {
                throw new AssertionError("action must not run while the lock is held");
            });
            assertEquals(false, ran);
        } finally {
            release.countDown();
            holder.join();
        }

        boolean ran = registry.tryWithLock(key, 5, TimeUnit.SECONDS, () -> {
        });
        assertTrue(ran);
    }

    @Test
    @Timeout(30)
    void differentKeysAreIndependent() {
        MapLockRegistry registry = new MapLockRegistry();

        Lock a1 = registry.obtain("a");
        Lock a2 = registry.obtain("a");
        Lock b = registry.obtain("b");

        assertEquals(a1, a2);
        assertNotSame(a1, b);

        assertTrue(a1.tryLock());
        assertTrue(b.tryLock());
        a1.unlock();
        b.unlock();
    }
}
