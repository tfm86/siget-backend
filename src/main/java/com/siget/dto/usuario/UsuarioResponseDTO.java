package com.siget.dto.usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String perfil,
        Boolean ativo
) {}



