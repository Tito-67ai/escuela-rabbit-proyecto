package com.escuela.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Rol {
    ROOT,
    ADMINISTRATIVO,
    DOCENTE,
    DIRECTOR,
    PRECEPTOR;

    /**
     * Acepta el rol sin importar mayusculas/minusculas ("docente", "Docente", "DOCENTE").
     */
    @JsonCreator
    public static Rol desde(String valor) {
        if (valor == null) {
            return null;
        }
        String normalizado = valor.trim().toUpperCase();
        for (Rol rol : values()) {
            if (rol.name().equals(normalizado)) {
                return rol;
            }
        }
        throw new IllegalArgumentException(
                "Rol invalido: '" + valor + "'. Valores permitidos: ROOT, ADMINISTRATIVO, DOCENTE, DIRECTOR, PRECEPTOR");
    }
}
