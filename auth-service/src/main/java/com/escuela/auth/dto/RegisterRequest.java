package com.escuela.auth.dto;

import com.escuela.auth.model.Rol;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterRequest(
    @JsonProperty("username") @JsonAlias({"nombreUsuario", "user"}) String nombreUsuario,
    @JsonProperty("password") @JsonAlias({"clave", "contrasena"}) String clave,
    String correo,
    @JsonProperty("rol") @JsonAlias({"role"}) Rol rol
) {}