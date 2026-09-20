package org.xzframework.integration.support.locks;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
