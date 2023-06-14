package org.xzframework.security.verification;

public interface DeviceVerificationCodeService {
    DeviceVerificationCode create(DeviceInfo device);

    boolean validate(String verifyId, String key, String verifyCode);
}
