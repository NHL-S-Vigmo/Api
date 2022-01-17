package com.nhlstenden.student.vigmo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TimeValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TimeValidator {
    String message() default "Time does not validate, use pattern 'HH:mm'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
