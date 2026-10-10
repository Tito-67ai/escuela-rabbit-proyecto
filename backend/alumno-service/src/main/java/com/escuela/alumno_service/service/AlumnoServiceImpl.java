package com.escuela.alumno_service.service;

import com.escuela.alumno_service.client.CursoClient;
import com.escuela.alumno_service.config.RabbitConfig;
import com.escuela.alumno_service.dto.AlumnoAltaDTO;
import com.escuela.alumno_service.dto.AlumnoConCursoDTO;
import com.escuela.alumno_service.dto.CursoDTO;
import com.escuela.alumno_service.entidad.Alumno;
import com.escuela.alumno_service.event.AlumnoInscriptoEvent;
import com.escuela.alumno_service.repository.AlumnoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private static final Logger log = LoggerFactory.getLogger(AlumnoServiceImpl.class);

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired(required = false)
    private CursoClient cursoClient;

    @Override
    public List<AlumnoConCursoDTO> obtenerTodos() {
        return alumnoRepository.findAll()
                .stream()
                .map(this::convertirAConCursoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoConCursoDTO obtenerPorId(Integer id) {
        return alumnoRepository.findById(id)
                .map(this::convertirAConCursoDTO)
                .orElse(null);
    }

    @Override
    public AlumnoConCursoDTO guardar(AlumnoAltaDTO altaDTO) {
        Alumno alumno = new Alumno();
        alumno.setNombre(altaDTO.nombre());
        alumno.setApellido(altaDTO.apellido());
        alumno.setDni(altaDTO.dni());
        alumno.setCursoId(altaDTO.cursoId());

        Alumno guardado = alumnoRepository.save(alumno);
        publicarInscripcion(guardado);
        return convertirAConCursoDTO(guardado);
    }

    private void publicarInscripcion(Alumno alumno) {
        if (alumno.getCursoId() == null) {
            return;
        }
        try {
            AlumnoInscriptoEvent evento = new AlumnoInscriptoEvent(
                    alumno.getId(), alumno.getNombre(), alumno.getApellido(), alumno.getCursoId());
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY_INSCRIPCION, evento);
            log.info("Evento de inscripcion publicado para el alumno {} en el curso {}",
                    alumno.getId(), alumno.getCursoId());
        } catch (Exception e) {
            log.warn("No se pudo publicar el evento de inscripcion del alumno {}: {}",
                    alumno.getId(), e.getMessage());
        }
    }

    @Override
    public AlumnoConCursoDTO actualizar(Integer id, AlumnoAltaDTO altaDTO) {
        return alumnoRepository.findById(id)
                .map(alumno -> {
                    alumno.setNombre(altaDTO.nombre());
                    alumno.setApellido(altaDTO.apellido());
                    alumno.setDni(altaDTO.dni());
                    alumno.setCursoId(altaDTO.cursoId());
                    Alumno actualizado = alumnoRepository.save(alumno);
                    return convertirAConCursoDTO(actualizado);
                })
                .orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        if (alumnoRepository.existsById(id)) {
            alumnoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private AlumnoConCursoDTO convertirAConCursoDTO(Alumno alumno) {
        AlumnoConCursoDTO dto = new AlumnoConCursoDTO();
        dto.setId(alumno.getId());
        dto.setNombre(alumno.getNombre());
        dto.setApellido(alumno.getApellido());
        dto.setDni(alumno.getDni());
        dto.setCursoId(alumno.getCursoId());
        dto.setCurso(obtenerCurso(alumno.getCursoId()));
        return dto;
    }

    private CursoDTO obtenerCurso(Integer cursoId) {
        if (cursoId == null || cursoClient == null) {
            return null;
        }
        try {
            return cursoClient.obtenerCursoPorId(obtenerTokenActual(), cursoId);
        } catch (Exception e) {
            log.warn("No se pudo obtener el curso {}: {}", cursoId, e.getMessage());
            return null;
        }
    }

    private String obtenerTokenActual() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                return request.getHeader("Authorization");
            }
        } catch (Exception ignored) {}
        return null;
    }
}
