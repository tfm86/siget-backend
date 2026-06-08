package com.siget.mappers;

import com.siget.dto.usuario.UsuarioCreateDTO;
import com.siget.dto.usuario.UsuarioResponseDTO;
import com.siget.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioCreateDTO dto) {

        return Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha())
                .perfil(dto.perfil())
                .ativo(true)
                .build();
    }

    public UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getAtivo()
        );
    }

}
