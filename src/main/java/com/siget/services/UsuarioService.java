package com.siget.services;

import com.siget.dto.usuario.UsuarioCreateDTO;
import com.siget.dto.usuario.UsuarioResponseDTO;
import com.siget.dto.usuario.UsuarioUpdateDTO;
import com.siget.entities.Usuario;
import com.siget.exceptions.EmailJaCadastradoException;
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

        if (repository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException(dto.email());
        }

        Usuario usuario = mapper.toEntity(dto);

        String codigo = gerarCodigo(dto.perfil());
        usuario.setCodigoUsuario(codigo);

        usuario.setSenha(encoder.encode(usuario.getSenha()));


        return mapper.toDTO(repository.save(usuario));
    }

    public void deletar(Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    private String gerarCodigo(String perfil) {

        String prefixo = switch (perfil.toUpperCase()) {
            case "TECNICO" -> "T";
            case "ADMIN" -> "A";
            case "FUNCIONARIO" -> "F";
            default -> "X";
        };

        long total = repository.countByPerfil(perfil) + 1;

        return prefixo + String.format("%07d", total);
    }


    public UsuarioResponseDTO atualizar(Long id, UsuarioUpdateDTO dto) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Se o e-mail mudou, verificar duplicidade
        if (!usuario.getEmail().equals(dto.email()) &&
                repository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException("O e-mail " + dto.email() + " já está cadastrado");
        }

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setPerfil(dto.perfil());
        usuario.setSenha(encoder.encode(dto.senha()));


        return mapper.toDTO(repository.save(usuario));
    }
}
