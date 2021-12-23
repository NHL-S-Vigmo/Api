package com.nhlstenden.student.vigmo.validators;

import com.nhlstenden.student.vigmo.models.WeekDay;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class WeekdayValidatorImpl implements ConstraintValidator<WeekdayValidator, String> {
    List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(WeekdayValidator constraintAnnotation) {
        valueList = new ArrayList<>();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = WeekDay.class.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }
}
