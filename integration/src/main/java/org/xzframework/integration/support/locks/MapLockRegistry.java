package org.xzframework.integration.support.locks;

import java.io.Serial;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MapLockRegistry implements LockRegistry<Lock> {

    private final ConcurrentHashMap<Object, Lock> map = new ConcurrentHashMap<>();

    @Override
    public Lock obtain(Object lockKey) {
        return map.computeIfAbsent(lockKey, InnerLock::new);
    }

    private class InnerLock extends ReentrantLock {

        @Serial
        private static final long serialVersionUID = -733265318250367868L;

        private final Object lockKey;

        private InnerLock(Object lockKey) {
            this.lockKey = lockKey;
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                if (this.getHoldCount() <= 0 && !this.isLocked()) {
                    map.remove(lockKey, this);
                }
            }
        }

    }

}
