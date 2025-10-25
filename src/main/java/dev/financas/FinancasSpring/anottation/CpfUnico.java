package dev.financas.FinancasSpring.anottation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfUnicoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfUnico {
    String message() default "CPF já cadastrado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}