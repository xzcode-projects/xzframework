package org.xzframework.security.verification;

public interface ConcurrencyStrategy {
    // 尝试获取设备锁
    void tryLock(DeviceInfo device);
}
