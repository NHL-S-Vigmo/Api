package com.nhlstenden.student.vigmo.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidatorImpl implements ConstraintValidator<DateValidator, String> {
    @Override
    public void initialize(DateValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateObject = LocalDate.parse(value, format);
            return dateObject != null;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}