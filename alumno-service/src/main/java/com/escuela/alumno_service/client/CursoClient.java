package com.escuela.alumno_service.client;

import com.escuela.alumno_service.dto.CursoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "curso-service")
public interface CursoClient {

    @GetMapping("/cursos/{id}")
    CursoDTO obtenerCursoPorId(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("id") Integer id);
}
