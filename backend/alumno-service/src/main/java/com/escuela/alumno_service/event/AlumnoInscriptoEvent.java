package com.escuela.alumno_service.event;

/**
 * Evento que publica alumno-service cuando se inscribe (crea) un alumno en un curso.
 */
public record AlumnoInscriptoEvent(Integer alumnoId, String nombre, String apellido, Integer cursoId) {
}
