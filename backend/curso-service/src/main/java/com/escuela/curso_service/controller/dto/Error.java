package com.escuela.curso_service.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Error {

    private int codigo;
    private LocalDateTime timestamp;
    private String detail;

    public Error(int codigo, LocalDateTime timestamp, String detail) {
        this.codigo = codigo;
        this.timestamp = timestamp;
        this.detail = detail;
    }
}
