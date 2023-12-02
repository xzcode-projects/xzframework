package com.xzframework.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class DataForbiddenException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2182641842211384057L;

    public DataForbiddenException(String message) {
        super(message);
    }

    public DataForbiddenException(String message, Throwable err) {
        super(message, err);
    }

    public DataForbiddenException() {
        super("权限不足");
    }
}
