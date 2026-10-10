package com.escuela.curso_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CursoAltaDTO(
    @NotBlank(message = "El nombre del curso es obligatorio")
    String nombre,

    @NotNull(message = "El docente es obligatorio")
    Integer docenteId,

    @NotNull(message = "El cupo es obligatorio")
    @PositiveOrZero(message = "El cupo no puede ser negativo")
    Integer cupo,

    @NotBlank(message = "La materia es obligatoria")
    String materia,

    String horario
) {}
