package com.nhlstenden.student.vigmo.dto;

import java.util.ArrayList;
import java.util.Arrays;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.dto.SlideDto;
import com.nhlstenden.student.vigmo.dto.TextSlideDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void WeekDayValidation() {
        //Test that all days of the week are valid
        ArrayList<String> weekDays = new ArrayList<>(Arrays.asList("MONDAY","TUESDAY", "WEDNESDAY","THURSDAY", "FRIDAY"));
        AvailabilityDto availabilityDto = new AvailabilityDto(null, 1L,"" ,"09:00","13:00");

        for(String weekDay : weekDays){
            availabilityDto.setWeekDay(weekDay);
            boolean isValid = validator.validate(availabilityDto).isEmpty();
            assertThat(isValid).isTrue();
        }

        //Test that a non-existent day is invalid
        availabilityDto.setWeekDay("ABCDEF");
        assertThat(validator.validate(availabilityDto).isEmpty()).isFalse();
    }

    @Test
    void TimeValidation() {
        AvailabilityDto availabilityDto = new AvailabilityDto(null, 1L,"WEDNESDAY" ,"08:59","09:00");
        boolean isValid = validator.validate(availabilityDto).isEmpty();
        assertThat(isValid).isTrue();

        //end time can't be before start time
        availabilityDto.setStartTime("09:00");
        isValid = validator.validate(availabilityDto).isEmpty();
        assertThat(isValid).isFalse();

    }

    @Test
    void DateValidation() {
        SlideDto textSlideDto = new TextSlideDto(null, "Title", "Message", 1L, true, 1300, "2022-01-20", "2022-01-20", "09:00", "10:00");
        boolean isValid = validator.validate(textSlideDto).isEmpty();
        assertThat(isValid).isTrue();

        textSlideDto.setEndDate("2022-01-19");
        isValid = validator.validate(textSlideDto).isEmpty();
        assertThat(isValid).isFalse();
    }

    @Test
    void testThrowsDateTimeParseException(){
        //test that both date and time cannot be parsed
        SlideDto textSlideDto = new TextSlideDto(null, "Title", "Message", 1L, true, 1300, "2022-01-50", "2022-01-50", "09:aa", "10:AA");
        boolean isValid = validator.validate(textSlideDto).isEmpty();
        assertThat(isValid).isFalse();
    }

}