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
        boolean esRoot = authentication != null && authentication.isAuthenticated()
                && authentication.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_ROOT".equals(a.getAuthority()));

        // El auto-registro publico solo puede crear DOCENTE. Para asignar otro rol hay que estar como ROOT.
        if (!esRoot && request.rol() != Rol.DOCENTE) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    "No podes asignar el rol " + request.rol()
                    + ": solo un usuario ROOT puede hacerlo. Registrate con rol DOCENTE"
                    + " o autoriza con el token de admin (admin/admin777).");
        }

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
