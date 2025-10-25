package dev.financas.FinancasSpring.rest.dto;

import dev.financas.FinancasSpring.model.entities.UsuarioPreferencias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioPreferenciasResponseDTO {
    private Long id;
    private UsuarioPreferencias.TemaInterface temaInterface;
    private Boolean notificacoesAtivadas;
    private String moedaPreferida;
    private String avatarUrl;
}