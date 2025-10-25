package dev.financas.FinancasSpring.anottation;

import dev.financas.FinancasSpring.model.repository.UsuarioDetalhesRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CpfUnicoValidator implements ConstraintValidator<CpfUnico, String> {

    private final UsuarioDetalhesRepository usuarioDetalhesRepository;

    public CpfUnicoValidator(UsuarioDetalhesRepository usuarioDetalhesRepository) {
        this.usuarioDetalhesRepository = usuarioDetalhesRepository;
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(cpf))
            return true;

        return !usuarioDetalhesRepository.existsByCpf(cpf);
    }
}