package com.escuela.auth.error;

public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CredencialesInvalidasException(String message) {
        super(message);
    }
}
