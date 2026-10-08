package com.escuela.curso_service.client;

import com.escuela.curso_service.dto.DocenteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "admin-service")
public interface DocenteClient {

    @GetMapping("/admin/personal/{id}")
    DocenteDTO obtenerDocentePorId(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("id") Integer id);
}