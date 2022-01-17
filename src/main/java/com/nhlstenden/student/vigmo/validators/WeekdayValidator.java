package com.nhlstenden.student.vigmo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WeekdayValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Weekday must be set")
@ReportAsSingleViolation
public @interface WeekdayValidator {
    String message() default "Not a valid weekday, use either one of 'MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
