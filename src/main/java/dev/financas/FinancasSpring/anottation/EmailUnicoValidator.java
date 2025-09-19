package dev.financas.FinancasSpring.anottation;

import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EmailUnicoValidator implements ConstraintValidator<EmailUnico, String> {

    private final UsuarioRepository usuarioRepository;

    public EmailUnicoValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(email)) {
            return true;
        }
        return usuarioRepository.findByEmail(email).isEmpty();
    }
}
