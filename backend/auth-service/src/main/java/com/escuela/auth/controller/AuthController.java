package com.escuela.auth.controller;

import com.escuela.auth.dto.*;
import com.escuela.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROOT')")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegisterRequest request) {
        String mensaje = authService.registrar(request);
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
