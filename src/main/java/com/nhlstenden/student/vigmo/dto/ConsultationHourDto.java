package com.nhlstenden.student.vigmo.dto;

import com.nhlstenden.student.vigmo.validators.WeekdayValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationHourDto {
    private Long id;

    @ApiModelProperty(name = "description", value = "Description of the current hour")
    @Size(max = 220)
    @NotEmpty
    private String description;

    @ApiModelProperty(name = "weekDay", required = true, value = "The day this hour falls on")
    @WeekdayValidator
    private String weekDay;

    @ApiModelProperty(name = "startTime", required = true, value = "When the hour starts", example = "10:45:00")
    @NotNull
    private String startTime;

    @ApiModelProperty(name = "endTime", required = true, value = "When the hour ends", example = "11:30:00")
    @NotNull
    private String endTime;
}
