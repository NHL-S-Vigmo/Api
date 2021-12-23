package com.nhlstenden.student.vigmo.dto;

import com.nhlstenden.student.vigmo.validators.WeekdayValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {
    private Long id;

    @ApiModelProperty(name = "description", value = "The user this data belongs to\nSee [user-controller](#/user-controller) for more info")
    private Long userId;

    @ApiModelProperty(name = "weekDay", required = true, value = "The day of availability")
    @WeekdayValidator
    private String weekDay;

    @ApiModelProperty(name = "startTime", required = true, value = "When the hour starts", example = "10:45:00")
    @NotNull
    private String startTime;

    @ApiModelProperty(name = "endTime", required = true, value = "When the hour ends", example = "11:30:00")
    @NotNull
    private String endTime;
}
