package com.escuela.auth.config;

import com.escuela.auth.model.Rol;
import com.escuela.auth.model.Usuario;
import com.escuela.auth.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                Usuario admin = crearOActualizar(usuarioRepository, passwordEncoder, "admin", "admin777", "admin@escuela.com", Rol.ROOT);
                usuarioRepository.save(admin);

                Usuario preceptor = crearOActualizar(usuarioRepository, passwordEncoder, "preceptor", "preceptor123", "preceptor@escuela.com", Rol.PRECEPTOR);
                usuarioRepository.save(preceptor);

                Usuario docente = crearOActualizar(usuarioRepository, passwordEncoder, "docente", "docente123", "docente@escuela.com", Rol.DOCENTE);
                usuarioRepository.save(docente);

                Usuario director = crearOActualizar(usuarioRepository, passwordEncoder, "director", "director123", "director@escuela.com", Rol.DIRECTOR);
                usuarioRepository.save(director);

                Usuario administrativo = crearOActualizar(usuarioRepository, passwordEncoder, "administrativo", "administrativo123", "administrativo@escuela.com", Rol.ADMINISTRATIVO);
                usuarioRepository.save(administrativo);

                System.out.println("\n=================================================");
                System.out.println(">>> USUARIOS CREADOS/ACTUALIZADOS CON PERFILES <<<");
                System.out.println("  admin          / admin777          -> ROOT");
                System.out.println("  preceptor      / preceptor123      -> PRECEPTOR");
                System.out.println("  docente        / docente123        -> DOCENTE");
                System.out.println("  director       / director123       -> DIRECTOR");
                System.out.println("  administrativo / administrativo123 -> ADMINISTRATIVO");
                System.out.println("=================================================\n");
            } catch (Exception e) {
                System.out.println("Error inicializando datos: " + e.getMessage());
            }
        };
    }

    private Usuario crearOActualizar(UsuarioRepository repository, PasswordEncoder encoder,
                                     String nombreUsuario, String clave, String correo, Rol rol) {
        Usuario usuario = repository.findByNombreUsuario(nombreUsuario).orElse(
                Usuario.builder()
                        .nombreUsuario(nombreUsuario)
                        .correo(correo)
                        .build()
        );
        usuario.setClave(encoder.encode(clave));
        usuario.setRol(rol);
        return usuario;
    }
}