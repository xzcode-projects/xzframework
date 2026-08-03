package org.xzframework.integration.support.locks;

import java.util.concurrent.locks.Lock;

@FunctionalInterface
public interface LockRegistry<L extends Lock> {

    /**
     * Obtain the lock associated with the parameter object.
     *
     * @param lockKey The object with which the lock is associated.
     * @return The associated lock.
     */
    L obtain(Object lockKey);

}
