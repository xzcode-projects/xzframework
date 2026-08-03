package com.xzframework.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.NoSuchElementException;

/**
 * 这个异常不建议使用， 推荐使用{@link NoSuchElementException}异常
 */
@Deprecated(forRemoval = true, since = "1.3.2")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DataNotExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8462512150406664119L;

    public DataNotExistException(String message) {
        super(message);
    }

    public DataNotExistException(String message, Throwable err) {
        super(message, err);
    }

    public DataNotExistException() {
        super("资源不存在");
    }
}
