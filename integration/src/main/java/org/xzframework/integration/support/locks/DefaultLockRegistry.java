package org.xzframework.integration.support.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A default implementation of {@link LockRegistry} that uses a
 * {@link java.util.concurrent.locks.ReentrantLock} for each key.
 * <p>
 * This implementation is derived from Spring Integration's
 * {@code org.springframework.integration.support.locks.DefaultLockRegistry},
 * licensed under the Apache License, Version 2.0.
 * Modifications: removed the dependency on Spring's {@code Assert} and replaced
 * it with plain {@code IllegalArgumentException} checks.
 *
 * @see <a href="https://github.com/spring-projects/spring-integration">Spring Integration</a>
 */

public class DefaultLockRegistry implements LockRegistry<Lock> {


    private final Lock[] lockTable;

    private final int mask;

    /**
     * Construct a DefaultLockRegistry with the default
     * mask 0xFF with 256 locks.
     */
    public DefaultLockRegistry() {
        this(0xFF); // NOSONAR magic number
    }

    /**
     * Construct a DefaultLockRegistry with the supplied
     * mask - the mask must have a value Math.pow(2, n) - 1 where n
     * is 1 to 31, creating a hash of Math.pow(2, n) locks.
     * <p> Examples:
     * <ul>
     * <li>0x3ff (1023) - 1024 locks</li>
     * <li>0xfff (4095) - 4096 locks</li>
     * </ul>
     *
     * @param mask The bit mask.
     */
    public DefaultLockRegistry(int mask) {
        String bits = Integer.toBinaryString(mask);
        if (bits.length() >= 32 || (mask != 0 && bits.lastIndexOf('0') > bits.indexOf('1'))) {
            throw new IllegalArgumentException("Mask must be a power of 2 - 1"); // NOSONAR magic number
        }
        this.mask = mask;
        int arraySize = this.mask + 1;
        this.lockTable = new ReentrantLock[arraySize];
        for (int i = 0; i < arraySize; i++) {
            this.lockTable[i] = new ReentrantLock();
        }
    }

    /**
     * Obtain a lock by masking the lockKey's hashCode() with
     * the mask and using the result as an index to the lock table.
     *
     * @param lockKey the object used to derive the lock index.
     */
    @Override
    public Lock obtain(Object lockKey) {
        if (lockKey == null) {
            throw new IllegalArgumentException("'lockKey' must not be null");
        }
        int lockIndex = lockKey.hashCode() & this.mask;
        return this.lockTable[lockIndex];
    }
}
