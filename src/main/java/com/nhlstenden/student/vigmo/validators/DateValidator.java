package com.nhlstenden.student.vigmo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateValidator {
    String message() default "Date does not validate, use pattern 'yyyy-MM-dd'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
