package dev.financas.FinancasSpring.rest.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import dev.financas.FinancasSpring.anottation.EmailUnico;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioCreateDTO {

    @NotBlank(message = "O nome é obrigatório!")
    @Size(min = 5, max = 100, message = "O nome deve ter entre 5 e 100 caracteres")
    private String NomeCompleto;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres")
    @EmailUnico
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 50, message = "A senha deve ter entre 6 e 50 caracteres")
    private String senha;

}
