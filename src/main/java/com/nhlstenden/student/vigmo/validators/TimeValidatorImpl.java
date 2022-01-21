package com.nhlstenden.student.vigmo.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValidatorImpl implements ConstraintValidator<TimeValidator, String> {
    @Override
    public void initialize(TimeValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime timeObject = LocalTime.parse(value, format);
            return timeObject != null;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
