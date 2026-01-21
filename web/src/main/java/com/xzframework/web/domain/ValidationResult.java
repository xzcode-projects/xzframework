package com.xzframework.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.jspecify.annotations.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@JsonInclude(value = Include.NON_EMPTY)
public class ValidationResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 579492257630348981L;

    private final boolean success;

    private final String message;

    private ValidationResult(boolean result, @NonNull String message) {
        this.success = result;
        this.message = message;
    }

    @NonNull
    public static ValidationResult success() {
        return new ValidationResult(true, "");
    }

    @NonNull
    public static ValidationResult failure() {
        return new ValidationResult(false, "");
    }

    @NonNull
    public static ValidationResult success(@NonNull String message) {
        return new ValidationResult(true, message);
    }

    @NonNull
    public static ValidationResult failure(@NonNull String message) {
        return new ValidationResult(false, message);
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationResult that = (ValidationResult) o;
        return success == that.success && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message);
    }

    public boolean isSuccess() {
        return success;
    }

}
