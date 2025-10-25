package dev.financas.FinancasSpring.rest.dto;

import dev.financas.FinancasSpring.model.entities.UsuarioPreferencias;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPreferenciasUpdateDTO {

    private UsuarioPreferencias.TemaInterface temaInterface;

    private Boolean notificacoesAtivadas;

    @Size(max = 10, message = "A moeda preferida deve ter no máximo 10 caracteres.")
    private String moedaPreferida;

    @URL(message = "A URL do avatar é inválida.")
    @Size(max = 500, message = "A URL do avatar deve ter no máximo 500 caracteres.")
    private String avatarUrl;
}