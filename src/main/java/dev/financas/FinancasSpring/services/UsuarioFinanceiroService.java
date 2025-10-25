package dev.financas.FinancasSpring.services;

import dev.financas.FinancasSpring.exceptions.ResourceNotFoundException;
import dev.financas.FinancasSpring.model.entities.Usuario;
import dev.financas.FinancasSpring.model.entities.UsuarioFinanceiro;
import dev.financas.FinancasSpring.model.repository.UsuarioFinanceiroRepository;
import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import dev.financas.FinancasSpring.rest.dto.UsuarioFinanceiroUpdateDTO;
import dev.financas.FinancasSpring.rest.mapper.UsuarioFinanceiroMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioFinanceiroService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioFinanceiroRepository usuarioFinanceiroRepository;
    private final UsuarioFinanceiroMapper usuarioFinanceiroMapper;

    public UsuarioFinanceiroService(UsuarioRepository usuarioRepository,
            UsuarioFinanceiroRepository usuarioFinanceiroRepository,
            UsuarioFinanceiroMapper usuarioFinanceiroMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioFinanceiroRepository = usuarioFinanceiroRepository;
        this.usuarioFinanceiroMapper = usuarioFinanceiroMapper;
    }

    @Transactional(readOnly = true)
    public UsuarioFinanceiro findByUsuarioId(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado com o ID: " + usuarioId);
        }
        return usuarioFinanceiroRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dados financeiros não encontrados para o usuário com ID: " + usuarioId));
    }

    @Transactional
    public UsuarioFinanceiro createOrUpdate(Long usuarioId, UsuarioFinanceiroUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + usuarioId));

        UsuarioFinanceiro financeiro = usuario.getFinanceiro();

        if (financeiro == null) {
            financeiro = usuarioFinanceiroMapper.toEntity(dto);
            financeiro.setUsuario(usuario);
            usuario.setFinanceiro(financeiro);
        } else {
            usuarioFinanceiroMapper.updateEntity(financeiro, dto);
        }

        usuarioRepository.save(usuario);

        return financeiro;
    }
}