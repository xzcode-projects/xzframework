package org.xzframework.security.verification;

import java.io.Serial;

public class ConcurrencyException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8922944591202219514L;

    public ConcurrencyException() {
        this("设备验证码间隔时间太短");
    }

    public ConcurrencyException(String message) {
        super(message);
    }
}
