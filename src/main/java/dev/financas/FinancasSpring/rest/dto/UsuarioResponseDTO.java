package dev.financas.FinancasSpring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private Long id;
    private String nomeCompleto;
    private String email;
    private String status;
    private String role;
    private OffsetDateTime criadoEm;
}
