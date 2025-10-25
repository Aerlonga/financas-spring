package dev.financas.FinancasSpring.rest.mapper;

import dev.financas.FinancasSpring.model.entities.Usuario;
import dev.financas.FinancasSpring.model.entities.UsuarioDetalhes;
import dev.financas.FinancasSpring.model.entities.UsuarioFinanceiro;
import dev.financas.FinancasSpring.model.entities.UsuarioPreferencias;
import dev.financas.FinancasSpring.rest.dto.UsuarioCreateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioUpdateDTO;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioDetalhesMapper usuarioDetalhesMapper;
    private final UsuarioFinanceiroMapper usuarioFinanceiroMapper;
    private final UsuarioPreferenciasMapper usuarioPreferenciasMapper;

    public UsuarioMapper(PasswordEncoder passwordEncoder,
            UsuarioDetalhesMapper usuarioDetalhesMapper,
            UsuarioFinanceiroMapper usuarioFinanceiroMapper,
            UsuarioPreferenciasMapper usuarioPreferenciasMapper) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioDetalhesMapper = usuarioDetalhesMapper;
        this.usuarioFinanceiroMapper = usuarioFinanceiroMapper;
        this.usuarioPreferenciasMapper = usuarioPreferenciasMapper;
    }

    // DTO → Entity
    public Usuario toEntity(UsuarioCreateDTO dto) {
        Usuario usuario = Usuario.builder()
                .nomeCompleto(dto.getNomeCompleto())
                .email(dto.getEmail())
                .senhaHash(passwordEncoder.encode(dto.getSenha()))
                .role(dto.getRole())
                .build();

        if (dto.getDetalhes() != null) {
            UsuarioDetalhes detalhes = usuarioDetalhesMapper.toEntity(dto.getDetalhes());
            usuario.setDetalhes(detalhes);
        }
        if (dto.getFinanceiro() != null) {
            UsuarioFinanceiro financeiro = usuarioFinanceiroMapper.toEntity(dto.getFinanceiro());
            usuario.setFinanceiro(financeiro);
        }
        if (dto.getPreferencias() != null) {
            UsuarioPreferencias preferencias = usuarioPreferenciasMapper.toEntity(dto.getPreferencias());
            usuario.setPreferencias(preferencias);
        }

        return usuario;
    }

    // Entity → ResponseDTO
    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nomeCompleto(usuario.getNomeCompleto())
                .email(usuario.getEmail())
                .status(usuario.getStatus().name())
                .role(usuario.getRole().name())
                .criadoEm(usuario.getCriadoEm())
                .criadoPor(usuario.getCriadoPor())
                .atualizadoEm(usuario.getAtualizadoEm())
                .atualizadoPor(usuario.getAtualizadoPor())
                // Mapeia as entidades relacionadas para seus respectivos DTOs de resposta
                .detalhes(usuario.getDetalhes() != null
                        ? usuarioDetalhesMapper.toResponseDTO(usuario.getDetalhes())
                        : null)
                .financeiro(usuario.getFinanceiro() != null
                        ? usuarioFinanceiroMapper.toResponseDTO(usuario.getFinanceiro())
                        : null)
                .preferencias(usuario.getPreferencias() != null
                        ? usuarioPreferenciasMapper.toResponseDTO(usuario.getPreferencias())
                        : null)
                .build();
    }

    public void updateEntity(Usuario usuario, UsuarioUpdateDTO dto) {
        if (dto.getNomeCompleto() != null) {
            usuario.setNomeCompleto(dto.getNomeCompleto());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getSenha() != null) {
            usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        }
        if (dto.getStatus() != null) {
            usuario.setStatus(dto.getStatus());
        }
        if (dto.getRole() != null) {
            usuario.setRole(dto.getRole());
        }

        // Atualiza as entidades relacionadas, se os DTOs de atualização forem
        // fornecidos
        if (dto.getDetalhes() != null && usuario.getDetalhes() != null) {
            usuarioDetalhesMapper.updateEntity(usuario.getDetalhes(), dto.getDetalhes());
        } else if (dto.getDetalhes() != null && usuario.getDetalhes() == null) {
            // Se não existia e foi fornecido no update, cria uma nova entidade de detalhes
            usuario.setDetalhes(usuarioDetalhesMapper.toEntity(dto.getDetalhes()));
        }
        if (dto.getFinanceiro() != null && usuario.getFinanceiro() != null) {
            usuarioFinanceiroMapper.updateEntity(usuario.getFinanceiro(), dto.getFinanceiro());
        } else if (dto.getFinanceiro() != null && usuario.getFinanceiro() == null) {
            usuario.setFinanceiro(usuarioFinanceiroMapper.toEntity(dto.getFinanceiro()));
        }
        if (dto.getPreferencias() != null && usuario.getPreferencias() != null) {
            usuarioPreferenciasMapper.updateEntity(usuario.getPreferencias(), dto.getPreferencias());
        } else if (dto.getPreferencias() != null && usuario.getPreferencias() == null) {
            usuario.setPreferencias(usuarioPreferenciasMapper.toEntity(dto.getPreferencias()));
        }
    }
}
