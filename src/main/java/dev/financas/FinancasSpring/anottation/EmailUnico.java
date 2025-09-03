package dev.financas.FinancasSpring.anottation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = EmailUnicoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnico {
    String message() default "Email já cadastrado";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
