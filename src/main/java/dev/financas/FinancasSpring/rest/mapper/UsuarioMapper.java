package dev.financas.FinancasSpring.rest.mapper;

import dev.financas.FinancasSpring.model.entities.Usuario;
import dev.financas.FinancasSpring.rest.dto.UsuarioCreateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioResponseDTO;
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
                .build();
    }
}
