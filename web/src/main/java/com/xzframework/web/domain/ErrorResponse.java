package com.xzframework.web.domain;

import java.time.ZonedDateTime;

public record ErrorResponse(int status, ZonedDateTime timestamp, String error, String message, String path) {
    public ErrorResponse() {
        this(500);
    }

    public ErrorResponse(int status) {
        this(status, null, null, null);
    }

    public ErrorResponse(int status, String error, String message, String path) {
        this(status, ZonedDateTime.now(), error, message, path);
    }

}
