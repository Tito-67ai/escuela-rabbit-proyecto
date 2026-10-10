package com.escuela.curso_service.service;

import com.escuela.curso_service.dto.CursoAltaDTO;
import com.escuela.curso_service.dto.CursoConDocenteDTO;

import java.util.List;

public interface CursoService {
    List<CursoConDocenteDTO> obtenerTodos();
    CursoConDocenteDTO obtenerPorId(Integer id);
    CursoConDocenteDTO guardar(CursoAltaDTO altaDTO);
    CursoConDocenteDTO actualizar(Integer id, CursoAltaDTO altaDTO);
    boolean eliminar(Integer id);
    boolean activar(Integer id);
}
