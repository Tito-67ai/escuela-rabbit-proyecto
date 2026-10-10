package com.escuela.alumno_service.controller;

import com.escuela.alumno_service.dto.AlumnoAltaDTO;
import com.escuela.alumno_service.dto.AlumnoConCursoDTO;
import com.escuela.alumno_service.error.RecursoNoEncontradoException;
import com.escuela.alumno_service.service.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public List<AlumnoConCursoDTO> listarTodos() {
        return alumnoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoConCursoDTO> obtenerPorId(@PathVariable Integer id) {
        AlumnoConCursoDTO alumno = alumnoService.obtenerPorId(id);
        if (alumno == null) {
            throw new RecursoNoEncontradoException("No existe el alumno con id " + id);
        }
        return ResponseEntity.ok(alumno);
    }

    @PostMapping
    @PreAuthorize("hasRole('PRECEPTOR')")
    public ResponseEntity<AlumnoConCursoDTO> crear(@Valid @RequestBody AlumnoAltaDTO altaDTO) {
        AlumnoConCursoDTO creado = alumnoService.guardar(altaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRECEPTOR')")
    public ResponseEntity<AlumnoConCursoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody AlumnoAltaDTO altaDTO) {
        AlumnoConCursoDTO actualizado = alumnoService.actualizar(id, altaDTO);
        if (actualizado == null) {
            throw new RecursoNoEncontradoException("No existe el alumno con id " + id);
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PRECEPTOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!alumnoService.eliminar(id)) {
            throw new RecursoNoEncontradoException("No existe el alumno con id " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
