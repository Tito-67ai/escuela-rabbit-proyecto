package com.escuela.curso_service.listener;

import com.escuela.curso_service.config.RabbitConfig;
import com.escuela.curso_service.event.AlumnoInscriptoEvent;
import com.escuela.curso_service.repository.CursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Consumidor RabbitMQ: al recibir una inscripcion, descuenta un lugar del cupo del curso.
 */
@Component
public class AlumnoInscriptoListener {

    private static final Logger log = LoggerFactory.getLogger(AlumnoInscriptoListener.class);

    @Autowired
    private CursoRepository cursoRepository;

    @RabbitListener(queues = RabbitConfig.INSCRIPCIONES_QUEUE)
    @Transactional
    public void recibirInscripcion(AlumnoInscriptoEvent evento) {
        if (evento == null || evento.cursoId() == null) {
            log.warn("Evento de inscripcion invalido recibido: {}", evento);
            return;
        }

        cursoRepository.findById(evento.cursoId()).ifPresentOrElse(curso -> {
            Integer cupo = curso.getCupo();
            if (cupo != null && cupo > 0) {
                curso.setCupo(cupo - 1);
                cursoRepository.save(curso);
                log.info("Cupo del curso {} ({}) descontado. Cupo restante: {}",
                        curso.getId(), curso.getNombre(), curso.getCupo());
            } else {
                log.warn("El curso {} no tiene cupo disponible", curso.getId());
            }
        }, () -> log.warn("No se encontro el curso {} para la inscripcion del alumno {}",
                evento.cursoId(), evento.alumnoId()));
    }
}
