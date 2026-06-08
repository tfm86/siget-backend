package com.siget.repositories;

import com.siget.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    boolean existsByEmail(String email);
    long countByPerfil(String perfil);
}
