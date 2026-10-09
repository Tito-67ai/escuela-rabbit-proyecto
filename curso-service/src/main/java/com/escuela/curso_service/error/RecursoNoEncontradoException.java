package com.escuela.curso_service.error;

public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RecursoNoEncontradoException(String message) {
        super(message);
    }
}
