package dev.financas.FinancasSpring.anottation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailUnicoUpdateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnicoUpdate {
    String message() default "E-mail já está em uso por outro usuário";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
