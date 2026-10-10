package com.escuela.auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @JsonProperty("username") @JsonAlias({"nombreUsuario", "user"})
    @NotBlank(message = "El nombre de usuario es obligatorio")
    String nombreUsuario,

    @JsonProperty("password") @JsonAlias({"clave", "contrasena"})
    @NotBlank(message = "La contraseña es obligatoria")
    String clave
) {}
