package com.nhlstenden.student.vigmo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StartEndTimeValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ReportAsSingleViolation
public @interface StartEndTimeValidator {
    String message() default "Start time needs to be before the end time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
