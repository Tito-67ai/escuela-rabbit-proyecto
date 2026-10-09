package com.escuela.auth.error;

public class UsuarioYaExisteException extends RuntimeException {

    public UsuarioYaExisteException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UsuarioYaExisteException(String message) {
        super(message);
    }
}
