package com.escuela.auth.repository;

import com.escuela.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Boolean existsByNombreUsuario(String nombreUsuario);
}