package dev.financas.FinancasSpring.service;

import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import dev.financas.FinancasSpring.model.entities.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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

//    public Optional<Usuario> atualizar(Long id, Usuario usuarioAtualizado) {
//        return usuarioRepository.findById(id).map(usuario -> {
//            usuario.setNomeCompleto(usuarioAtualizado.getNomeCompleto());
//            usuario.setEmail(usuarioAtualizado.getEmail());
//            usuario.setSenhaHash(usuarioAtualizado.getSenhaHash());
//            usuario.setStatus(usuarioAtualizado.getStatus());
//            usuario.setRole(usuarioAtualizado.getRole());
//            return usuarioRepository.save(usuario);
//        });
//    }

}
