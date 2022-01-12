package com.nhlstenden.student.vigmo.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BcryptValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BcryptValidator {
    String message() default "Not a valid bcrypt encrypted string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
