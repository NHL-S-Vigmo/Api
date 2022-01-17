package com.nhlstenden.student.vigmo.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class TimeValidatorImpl implements ConstraintValidator<TimeValidator, String> {
    @Override
    public void initialize(TimeValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
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
