package com.escuela.curso_service.event;

/**
 * Copia del evento que publica alumno-service cuando se inscribe un alumno en un curso.
 */
public record AlumnoInscriptoEvent(Integer alumnoId, String nombre, String apellido, Integer cursoId) {
}
