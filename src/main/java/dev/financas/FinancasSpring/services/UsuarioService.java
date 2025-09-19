package dev.financas.FinancasSpring.services;

import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import dev.financas.FinancasSpring.rest.dto.UsuarioUpdateDTO;
import dev.financas.FinancasSpring.rest.mapper.UsuarioMapper;
import dev.financas.FinancasSpring.model.entities.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> atualizar(Long id, UsuarioUpdateDTO dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuarioMapper.updateEntity(usuario, dto);
            return usuarioRepository.save(usuario);
        });
    }

}
