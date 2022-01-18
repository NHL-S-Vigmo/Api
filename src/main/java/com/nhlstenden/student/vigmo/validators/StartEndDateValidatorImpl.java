package com.nhlstenden.student.vigmo.validators;

import com.nhlstenden.student.vigmo.dto.logic.StartEndDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StartEndDateValidatorImpl implements ConstraintValidator<StartEndDateValidator, StartEndDate> {
    @Override
    public void initialize(StartEndDateValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(StartEndDate startEndTimeData, ConstraintValidatorContext constraintValidatorContext) {
        if(startEndTimeData.getEndDate() == null) return true;

        if(startEndTimeData.getStartDate() == null ) return true;

        try{
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(startEndTimeData.getStartDate(), format);
            LocalDate endDate = LocalDate.parse(startEndTimeData.getEndDate(), format);
            return startDate.isBefore(endDate) || startDate.isEqual(endDate);
        }catch(Exception ex){
            return false;
        }
    }
}