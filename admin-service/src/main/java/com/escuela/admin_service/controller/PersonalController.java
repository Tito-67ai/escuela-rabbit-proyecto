package com.escuela.admin_service.controller;

import com.escuela.admin_service.dto.PersonalAltaDTO;
import com.escuela.admin_service.dto.PersonalDTO;
import com.escuela.admin_service.error.RecursoNoEncontradoException;
import com.escuela.admin_service.service.PersonalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/personal")
public class PersonalController {

    @Autowired
    private PersonalService docenteService;

    @GetMapping
    public List<PersonalDTO> listarTodos() {
        return docenteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalDTO> obtenerPorId(@PathVariable Integer id) {
        PersonalDTO docente = docenteService.obtenerPorId(id);
        if (docente == null) {
            throw new RecursoNoEncontradoException("No existe el personal con id " + id);
        }
        return ResponseEntity.ok(docente);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROOT', 'ADMINISTRATIVO')")
    public ResponseEntity<PersonalDTO> crear(@Valid @RequestBody PersonalAltaDTO altaDTO) {
        PersonalDTO creado = docenteService.guardar(altaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMINISTRATIVO')")
    public ResponseEntity<PersonalDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody PersonalAltaDTO altaDTO) {
        PersonalDTO actualizado = docenteService.actualizar(id, altaDTO);
        if (actualizado == null) {
            throw new RecursoNoEncontradoException("No existe el personal con id " + id);
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMINISTRATIVO')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!docenteService.eliminar(id)) {
            throw new RecursoNoEncontradoException("No existe el personal con id " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMINISTRATIVO')")
    public ResponseEntity<Void> activar(@PathVariable Integer id) {
        if (!docenteService.activar(id)) {
            throw new RecursoNoEncontradoException("No existe el personal con id " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
