package com.nhlstenden.student.vigmo.dto;

import com.nhlstenden.student.vigmo.validators.WeekdayValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationHourDto {
    private Long id;

    @ApiModelProperty(notes = "Description of the current hour", name = "description", required = false, value = "Ochtend spreekuur")
    @Size(max = 220)
    @NotEmpty
    private String description;

    @ApiModelProperty(notes = "The day this hour falls on", name = "weekDay", required = true, value = "MONDAY")
    @WeekdayValidator
    private String weekDay;

    @ApiModelProperty(notes = "When the hour starts", name = "startTime", required = true, value = "10:45:00")
    @NotNull
    private String startTime;

    @ApiModelProperty(notes = "When the hour ends", name = "endTime", required = true, value = "11:30:00")
    @NotNull
    private String endTime;
}
