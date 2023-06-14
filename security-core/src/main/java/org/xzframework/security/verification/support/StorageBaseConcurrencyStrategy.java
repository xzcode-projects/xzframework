package org.xzframework.security.verification.support;


import org.xzframework.security.verification.ConcurrencyException;
import org.xzframework.security.verification.ConcurrencyStrategy;
import org.xzframework.security.verification.DeviceInfo;
import org.xzframework.security.verification.DeviceVerificationCodeStorage;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class StorageBaseConcurrencyStrategy implements ConcurrencyStrategy {
    private final DeviceVerificationCodeStorage codeStorage;


    public StorageBaseConcurrencyStrategy(DeviceVerificationCodeStorage codeStorage) {
        this.codeStorage = codeStorage;
    }

    @Override
    public void tryLock(DeviceInfo device) {
        String deviceKey = device.getDeviceKey();
        codeStorage.findTopByKeyOrderByCreatedDateDesc(deviceKey)
                .filter(it -> it.getCreatedTime().plus(1, ChronoUnit.MINUTES).isAfter(ZonedDateTime.now()))
                .ifPresent(it -> {
                    throw new ConcurrencyException();
                });
    }
}
