package com.escuela.admin_service.dto;

import com.escuela.admin_service.entidad.TipoPersona;

public record PersonalDTO(
    Integer id,
    String nombre,
    String apellido,
    String dni,
    Float sueldo,
    String cargo,
    TipoPersona tipo
) {}