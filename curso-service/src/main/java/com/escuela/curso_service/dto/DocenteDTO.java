package com.escuela.curso_service.dto;

public record DocenteDTO(
    Integer id,
    String nombre,
    String apellido,
    String dni,
    Float sueldo,
    String cargo,
    String tipo
) {}