package com.nhlstenden.student.vigmo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StartEndDateValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ReportAsSingleViolation
public @interface StartEndDateValidator {
    String message() default "Start date needs to be before the end date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
