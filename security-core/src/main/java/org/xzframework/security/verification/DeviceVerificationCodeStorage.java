package org.xzframework.security.verification;

import java.util.Optional;

public interface DeviceVerificationCodeStorage {
    void remove(String verifyId);

    Optional<DeviceVerificationCode> findById(String id);

    DeviceVerificationCode save(DeviceVerificationCode code);

    Optional<DeviceVerificationCode> findTopByKeyOrderByCreatedDateDesc(String key);

}
