package com.escuela.auth.error;

public class TokenInvalidoException extends RuntimeException {

    public TokenInvalidoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public TokenInvalidoException(String message) {
        super(message);
    }
}
