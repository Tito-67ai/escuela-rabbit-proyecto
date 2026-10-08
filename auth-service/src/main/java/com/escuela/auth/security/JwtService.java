package com.escuela.auth.security;

import com.escuela.auth.model.Rol;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String claveSecreta;

    private final long TIEMPO_EXPIRACION = 86400000;

    private Key getLlaveFirma() {
        return Keys.hmacShaKeyFor(claveSecreta.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(String nombreUsuario) {
        return generarToken(nombreUsuario, null);
    }

    public String generarToken(String nombreUsuario, Rol rol) {
        String rolClaim = rol != null ? rol.name() : "DOCENTE";
        return Jwts.builder()
                .setSubject(nombreUsuario)
                .claim("rol", rolClaim)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TIEMPO_EXPIRACION))
                .signWith(getLlaveFirma(), SignatureAlgorithm.HS256)
                .compact();
    }
}