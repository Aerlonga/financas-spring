package dev.financas.FinancasSpring.anottation;

import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import dev.financas.FinancasSpring.rest.dto.UsuarioUpdateDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailUnicoUpdateValidator implements ConstraintValidator<EmailUnicoUpdate, UsuarioUpdateDTO> {

    private final UsuarioRepository usuarioRepository;

    public EmailUnicoUpdateValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean isValid(UsuarioUpdateDTO dto, ConstraintValidatorContext context) {
        if (dto.getEmail() == null) {
            return true;
        }

        var usuarioExistente = usuarioRepository.findByEmail(dto.getEmail());

        if (usuarioExistente.isEmpty()) {
            return true;
        }

        return usuarioExistente.get().getId().equals(dto.getId());
    }
}
