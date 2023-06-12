package org.xzframework.security.web.wx.mp.oauth2.authentication;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class StateValidationFailureException extends AuthenticationException {
    @Serial
    private static final long serialVersionUID = -2520716160962443850L;

    public StateValidationFailureException(String msg) {
        super(msg);
    }
}
