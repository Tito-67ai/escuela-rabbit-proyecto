package com.escuela.auth.security;

import com.escuela.auth.model.RefreshToken;
import com.escuela.auth.model.Usuario;
import com.escuela.auth.repository.RefreshTokenRepository;
import com.escuela.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final Long EXPIRACION_REFRESH_MS = 604800000L;

    @Transactional
    public RefreshToken crearRefreshToken(String nombreUsuario) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + nombreUsuario));

        refreshTokenRepository.findByUsuario(usuario).ifPresent(token -> {
            refreshTokenRepository.delete(token);
            refreshTokenRepository.flush();
        });

        RefreshToken refreshToken = RefreshToken.builder()
                .usuario(usuario)
                .token(UUID.randomUUID().toString())
                .fechaExpiracion(Instant.now().plusMillis(EXPIRACION_REFRESH_MS))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken verificarExpiracion(RefreshToken token) {
        if (token.getFechaExpiracion().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expirado.");
        }
        return token;
    }
}