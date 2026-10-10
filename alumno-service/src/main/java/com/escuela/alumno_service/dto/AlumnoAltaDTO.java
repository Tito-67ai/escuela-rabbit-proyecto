package com.escuela.alumno_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlumnoAltaDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    String apellido,

    @NotBlank(message = "El DNI es obligatorio")
    String dni,

    @NotNull(message = "El curso es obligatorio")
    Integer cursoId
) {}
