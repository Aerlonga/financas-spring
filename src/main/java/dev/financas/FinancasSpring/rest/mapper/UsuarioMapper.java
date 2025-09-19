package dev.financas.FinancasSpring.rest.mapper;

import dev.financas.FinancasSpring.model.entities.Usuario;
import dev.financas.FinancasSpring.rest.dto.UsuarioCreateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioUpdateDTO;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final PasswordEncoder passwordEncoder;

    public UsuarioMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // DTO → Entity
    public Usuario toEntity(UsuarioCreateDTO dto) {
        return Usuario.builder()
                .nomeCompleto(dto.getNomeCompleto())
                .email(dto.getEmail())
                .senhaHash(passwordEncoder.encode(dto.getSenha()))
                .role(dto.getRole() != null ? dto.getRole() : Usuario.Role.USER) // 👈 aqui!
                .build();
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
    }
}
