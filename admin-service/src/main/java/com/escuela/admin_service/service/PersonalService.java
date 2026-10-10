package com.escuela.admin_service.service;

import com.escuela.admin_service.dto.PersonalAltaDTO;
import com.escuela.admin_service.dto.PersonalDTO;
import java.util.List;

public interface PersonalService {
    List<PersonalDTO> obtenerTodos();
    PersonalDTO obtenerPorId(Integer id);
    PersonalDTO guardar(PersonalAltaDTO altaDTO);
    PersonalDTO actualizar(Integer id, PersonalAltaDTO altaDTO);
    boolean eliminar(Integer id);
    boolean activar(Integer id);
}
