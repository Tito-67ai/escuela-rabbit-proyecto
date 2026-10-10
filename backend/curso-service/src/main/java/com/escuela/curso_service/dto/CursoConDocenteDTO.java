package com.escuela.curso_service.dto;

public record CursoConDocenteDTO(
    Integer id,
    String nombre,
    Integer docenteId,
    DocenteDTO docente,
    Integer cupo,
    String materia,
    String horario,
    Boolean activo
) {}
