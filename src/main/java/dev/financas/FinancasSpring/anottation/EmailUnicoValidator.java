package dev.financas.FinancasSpring.anottation;

import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailUnicoValidator implements ConstraintValidator<EmailUnico, String> {
    private final UsuarioRepository usuarioRepository;

    public EmailUnicoValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && usuarioRepository.findByEmail(email).isEmpty();
    }
}
