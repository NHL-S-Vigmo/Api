package com.nhlstenden.student.vigmo.dto;

import com.nhlstenden.student.vigmo.validators.WeekdayValidator;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Generated
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
    @DateTimeFormat(fallbackPatterns = {"hh:mm:ss", "hh:mm"})
    private String startTime;

    @ApiModelProperty(name = "endTime", required = true, value = "When the hour ends", example = "11:30:00")
    @NotNull
    @DateTimeFormat(fallbackPatterns = {"hh:mm:ss", "hh:mm"})
    private String endTime;
}
