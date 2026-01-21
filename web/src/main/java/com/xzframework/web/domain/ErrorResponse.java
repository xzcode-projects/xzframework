package com.xzframework.web.domain;

import org.jspecify.annotations.NonNull;

import java.time.ZonedDateTime;

public record ErrorResponse(int status, ZonedDateTime timestamp, String error, String message, String path) {

    public ErrorResponse() {
        this(500);
    }

    public ErrorResponse(int status) {
        this(status, "", "", "");
    }

    public ErrorResponse(int status, @NonNull String error, @NonNull String message, @NonNull String path) {
        this(status, ZonedDateTime.now(), error, message, path);
    }

}
