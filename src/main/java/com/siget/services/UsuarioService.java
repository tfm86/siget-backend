package com.siget.services;

import com.siget.dto.usuario.UsuarioCreateDTO;
import com.siget.dto.usuario.UsuarioResponseDTO;
import com.siget.entities.Usuario;
import com.siget.mappers.UsuarioMapper;
import com.siget.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder encoder;

    public UsuarioResponseDTO criar(UsuarioCreateDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return mapper.toDTO(repository.save(usuario));
    }


    public List<UsuarioResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }


}
