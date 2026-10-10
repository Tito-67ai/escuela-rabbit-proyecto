package com.escuela.auth.dto;

import com.escuela.auth.model.Rol;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @JsonProperty("username") @JsonAlias({"nombreUsuario", "user"})
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El nombre de usuario debe tener entre 4 y 50 caracteres")
    String nombreUsuario,

    @JsonProperty("password") @JsonAlias({"clave", "contrasena"})
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    String clave,

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    String correo,

    @JsonProperty("rol") @JsonAlias({"role"})
    @NotNull(message = "El rol es obligatorio")
    Rol rol
) {}
