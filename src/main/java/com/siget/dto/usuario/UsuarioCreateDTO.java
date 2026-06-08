package com.siget.dto.usuario;

public record UsuarioCreateDTO(

        String nome,
        String email,
        String senha,
        String perfil

) { }
