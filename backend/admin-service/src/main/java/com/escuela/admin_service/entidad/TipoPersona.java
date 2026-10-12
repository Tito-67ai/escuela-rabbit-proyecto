package com.escuela.admin_service.entidad;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoPersona {
    DOCENTE,
    ADMINISTRATIVO,
    DIRECTIVO;

    /**
     * Acepta el tipo sin importar mayusculas/minusculas ("docente", "Docente", "DOCENTE").
     */
    @JsonCreator
    public static TipoPersona desde(String valor) {
        if (valor == null) {
            return null;
        }
        String normalizado = valor.trim().toUpperCase();
        for (TipoPersona tipo : values()) {
            if (tipo.name().equals(normalizado)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException(
                "Tipo invalido: '" + valor + "'. Valores permitidos: DOCENTE, ADMINISTRATIVO, DIRECTIVO");
    }
}
