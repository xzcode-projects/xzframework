package com.xzframework.web.domain;

import java.time.ZonedDateTime;

public class ErrorResponse {
    private final int status;
    private final ZonedDateTime timestamp;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponse() {
        this(500);
    }

    public ErrorResponse(int status) {
        this(status, null, null, null);
    }

    public ErrorResponse(int status, String error, String message, String path) {
        this(status, ZonedDateTime.now(), error, message, path);
    }

    public ErrorResponse(int status, ZonedDateTime timestamp, String error, String message, String path) {
        this.status = status;
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }


}
