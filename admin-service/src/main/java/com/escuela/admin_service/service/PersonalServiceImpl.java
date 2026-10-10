package com.escuela.admin_service.service;

import com.escuela.admin_service.dto.PersonalAltaDTO;
import com.escuela.admin_service.dto.PersonalDTO;
import com.escuela.admin_service.entidad.Personal;
import com.escuela.admin_service.repository.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalServiceImpl implements PersonalService {

    @Autowired
    private PersonalRepository docenteRepository;

    @Override
    public List<PersonalDTO> obtenerTodos() {
        return docenteRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonalDTO obtenerPorId(Integer id) {
        return docenteRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @Override
    public PersonalDTO guardar(PersonalAltaDTO altaDTO) {
        Personal personal = new Personal();
        personal.setNombre(altaDTO.nombre());
        personal.setApellido(altaDTO.apellido());
        personal.setDni(altaDTO.dni());
        personal.setSueldo(altaDTO.sueldo());
        personal.setCargo(altaDTO.cargo());
        personal.setTipo(altaDTO.tipo());

        Personal guardado = docenteRepository.save(personal);
        return convertirADTO(guardado);
    }

    @Override
    public PersonalDTO actualizar(Integer id, PersonalAltaDTO altaDTO) {
        return docenteRepository.findById(id)
                .map(personal -> {
                    personal.setNombre(altaDTO.nombre());
                    personal.setApellido(altaDTO.apellido());
                    personal.setDni(altaDTO.dni());
                    personal.setSueldo(altaDTO.sueldo());
                    personal.setCargo(altaDTO.cargo());
                    personal.setTipo(altaDTO.tipo());
                    Personal actualizado = docenteRepository.save(personal);
                    return convertirADTO(actualizado);
                })
                .orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        return docenteRepository.findById(id)
                .map(personal -> {
                    personal.setActivo(false);
                    docenteRepository.save(personal);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean activar(Integer id) {
        return docenteRepository.findById(id)
                .map(personal -> {
                    personal.setActivo(true);
                    docenteRepository.save(personal);
                    return true;
                })
                .orElse(false);
    }

    private PersonalDTO convertirADTO(Personal personal) {
        return new PersonalDTO(
                personal.getId(),
                personal.getNombre(),
                personal.getApellido(),
                personal.getDni(),
                personal.getSueldo(),
                personal.getCargo(),
                personal.getTipo(),
                personal.getActivo()
        );
    }
}
