package com.escuela.auth.service;

import com.escuela.auth.dto.*;
import com.escuela.auth.error.CredencialesInvalidasException;
import com.escuela.auth.error.TokenInvalidoException;
import com.escuela.auth.error.UsuarioYaExisteException;
import com.escuela.auth.model.RefreshToken;
import com.escuela.auth.model.Rol;
import com.escuela.auth.model.Usuario;
import com.escuela.auth.repository.UsuarioRepository;
import com.escuela.auth.security.JwtService;
import com.escuela.auth.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public String registrar(RegisterRequest request) {
        if (usuarioRepository.existsByNombreUsuario(request.nombreUsuario())) {
            throw new UsuarioYaExisteException("El nombre de usuario ya existe");
        }

        Rol rol = request.rol() != null ? request.rol() : Rol.DOCENTE;

        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.nombreUsuario())
                .clave(passwordEncoder.encode(request.clave()))
                .correo(request.correo())
                .rol(rol)
                .build();

        usuarioRepository.save(usuario);
        return "Usuario registrado con exito";
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if (request == null || request.nombreUsuario() == null || request.clave() == null) {
            throw new CredencialesInvalidasException("Credenciales invalidas");
        }

        Usuario usuario = usuarioRepository.findByNombreUsuario(request.nombreUsuario()).orElse(null);

        if (usuario == null || usuario.getClave() == null || !passwordEncoder.matches(request.clave(), usuario.getClave())) {
            throw new CredencialesInvalidasException("Credenciales invalidas");
        }

        String jwtToken = jwtService.generarToken(usuario.getNombreUsuario(), usuario.getRol());
        RefreshToken refreshToken = refreshTokenService.crearRefreshToken(usuario.getNombreUsuario());

        return new LoginResponse(jwtToken, refreshToken.getToken(), usuario.getNombreUsuario(), usuario.getRol());
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        if (request == null || request.refreshToken() == null) {
            throw new TokenInvalidoException("Refresh Token no valido");
        }

        Optional<RefreshToken> tokenOptional = refreshTokenService.findByToken(request.refreshToken());

        if (tokenOptional.isEmpty()) {
            throw new TokenInvalidoException("Refresh Token no valido");
        }

        RefreshToken refreshToken = refreshTokenService.verificarExpiracion(tokenOptional.get());
        Usuario usuario = refreshToken.getUsuario();
        String nuevoToken = jwtService.generarToken(usuario.getNombreUsuario(), usuario.getRol());

        return new LoginResponse(nuevoToken, request.refreshToken(), usuario.getNombreUsuario(), usuario.getRol());
    }
}
