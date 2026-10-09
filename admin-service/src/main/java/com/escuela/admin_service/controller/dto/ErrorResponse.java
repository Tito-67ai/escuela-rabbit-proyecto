package com.escuela.admin_service.controller.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
public class ErrorResponse {

    private HttpStatus status;
    private int codigo;
    private LocalDateTime timestamp;
    private List<String> errors;
    private String message;

    public ErrorResponse(HttpStatus status, LocalDateTime timestamp, List<String> errors) {
        this.status = status;
        this.timestamp = timestamp;
        this.errors = errors;
        this.codigo = status.value();
    }

    public ErrorResponse(HttpStatus status, String message, String error) {
        this.status = status;
        this.codigo = status.value();
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.errors = Arrays.asList(error);
    }
}
