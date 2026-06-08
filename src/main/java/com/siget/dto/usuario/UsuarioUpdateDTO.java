package com.siget.dto.usuario;

public record UsuarioUpdateDTO(
        String nome,
        String email,
        String senha,
        String perfil,
        Boolean ativo
) {}

