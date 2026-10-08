package com.escuela.alumno_service.service;

import com.escuela.alumno_service.dto.AlumnoAltaDTO;
import com.escuela.alumno_service.dto.AlumnoConCursoDTO;
import com.escuela.alumno_service.entidad.Alumno;
import com.escuela.alumno_service.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

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
        return convertirAConCursoDTO(guardado);
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
        return dto;
    }
}