package org.xzframework.security.verification;

import org.xzframework.security.verification.exception.CodeSenderException;

import java.util.concurrent.CompletableFuture;

public interface DeviceVerificationCodeSender {
    CompletableFuture<Void> sendAsync(DeviceVerificationCode code);

    void send(DeviceVerificationCode code) throws CodeSenderException;
}
