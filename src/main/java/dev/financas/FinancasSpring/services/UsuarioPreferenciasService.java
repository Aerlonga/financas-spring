package dev.financas.FinancasSpring.services;

import dev.financas.FinancasSpring.exceptions.ResourceNotFoundException;
import dev.financas.FinancasSpring.model.entities.Usuario;
import dev.financas.FinancasSpring.model.entities.UsuarioPreferencias;
import dev.financas.FinancasSpring.model.repository.UsuarioPreferenciasRepository;
import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import dev.financas.FinancasSpring.rest.dto.UsuarioPreferenciasUpdateDTO;
import dev.financas.FinancasSpring.rest.mapper.UsuarioPreferenciasMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioPreferenciasService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioPreferenciasRepository usuarioPreferenciasRepository;
    private final UsuarioPreferenciasMapper usuarioPreferenciasMapper;

    public UsuarioPreferenciasService(UsuarioRepository usuarioRepository,
            UsuarioPreferenciasRepository usuarioPreferenciasRepository,
            UsuarioPreferenciasMapper usuarioPreferenciasMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioPreferenciasRepository = usuarioPreferenciasRepository;
        this.usuarioPreferenciasMapper = usuarioPreferenciasMapper;
    }

    @Transactional(readOnly = true)
    public UsuarioPreferencias findByUsuarioId(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado com o ID: " + usuarioId);
        }
        return usuarioPreferenciasRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Preferências não encontradas para o usuário com ID: " + usuarioId));
    }

    @Transactional
    public UsuarioPreferencias createOrUpdate(Long usuarioId, UsuarioPreferenciasUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + usuarioId));

        UsuarioPreferencias preferencias = usuario.getPreferencias();

        if (preferencias == null) {
            preferencias = usuarioPreferenciasMapper.toEntity(dto);
            preferencias.setUsuario(usuario);
            usuario.setPreferencias(preferencias);
        } else {
            usuarioPreferenciasMapper.updateEntity(preferencias, dto);
        }

        usuarioRepository.save(usuario);

        return preferencias;
    }
}