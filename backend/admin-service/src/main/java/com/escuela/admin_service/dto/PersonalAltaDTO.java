package com.escuela.admin_service.dto;

import com.escuela.admin_service.entidad.TipoPersona;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PersonalAltaDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    String apellido,

    @NotBlank(message = "El DNI es obligatorio")
    String dni,

    @NotNull(message = "El sueldo es obligatorio")
    @PositiveOrZero(message = "El sueldo no puede ser negativo")
    Float sueldo,

    String cargo,

    @NotNull(message = "El tipo de personal es obligatorio")
    TipoPersona tipo
) {}
