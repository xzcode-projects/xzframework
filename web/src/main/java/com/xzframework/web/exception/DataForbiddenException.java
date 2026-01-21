package com.xzframework.web.exception;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class DataForbiddenException extends SecurityException {

    @Serial
    private static final long serialVersionUID = 2182641842211384057L;

    public DataForbiddenException(@NonNull String message) {
        super(message);
    }

    public DataForbiddenException(@NonNull String message, @Nullable Throwable err) {
        super(message, err);
    }

    public DataForbiddenException() {
        super("data forbidden");
    }

}
