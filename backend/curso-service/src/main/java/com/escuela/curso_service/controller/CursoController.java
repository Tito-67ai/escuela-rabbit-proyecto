package com.escuela.curso_service.controller;

import com.escuela.curso_service.dto.CursoAltaDTO;
import com.escuela.curso_service.dto.CursoConDocenteDTO;
import com.escuela.curso_service.error.RecursoNoEncontradoException;
import com.escuela.curso_service.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public List<CursoConDocenteDTO> listarTodos() {
        return cursoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoConDocenteDTO> obtenerPorId(@PathVariable Integer id) {
        CursoConDocenteDTO curso = cursoService.obtenerPorId(id);
        if (curso == null) {
            throw new RecursoNoEncontradoException("No existe el curso con id " + id);
        }
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROOT', 'PRECEPTOR')")
    public ResponseEntity<CursoConDocenteDTO> crear(@RequestBody CursoAltaDTO altaDTO) {
        CursoConDocenteDTO creado = cursoService.guardar(altaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'PRECEPTOR')")
    public ResponseEntity<CursoConDocenteDTO> actualizar(@PathVariable Integer id, @RequestBody CursoAltaDTO altaDTO) {
        CursoConDocenteDTO actualizado = cursoService.actualizar(id, altaDTO);
        if (actualizado == null) {
            throw new RecursoNoEncontradoException("No existe el curso con id " + id);
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'PRECEPTOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!cursoService.eliminar(id)) {
            throw new RecursoNoEncontradoException("No existe el curso con id " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
