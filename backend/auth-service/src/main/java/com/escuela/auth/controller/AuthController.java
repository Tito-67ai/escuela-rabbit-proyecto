package com.escuela.auth.controller;

import com.escuela.auth.dto.*;
import com.escuela.auth.model.Rol;
import com.escuela.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegisterRequest request,
                                       Authentication authentication) {
        // Solo un ROOT puede elegir el rol. El auto-registro publico siempre crea usuarios DOCENTE.
        boolean esRoot = authentication != null && authentication.isAuthenticated()
                && authentication.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_ROOT".equals(a.getAuthority()));

        RegisterRequest efectivo = esRoot ? request
                : new RegisterRequest(request.nombreUsuario(), request.clave(), request.correo(), Rol.DOCENTE);

        String mensaje = authService.registrar(efectivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        LoginResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }
}
