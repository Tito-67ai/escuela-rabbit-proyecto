package com.escuela.auth.dto;

import com.escuela.auth.model.Rol;

public record LoginResponse(
    String token,
    String refreshToken,
    String nombreUsuario,
    Rol rol
) {}