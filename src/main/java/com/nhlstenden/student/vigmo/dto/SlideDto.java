package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class SlideDto {
    private Long id;

    @Positive
    @NotNull
    private Long slideshowId;

    @NotNull
    private Boolean isActive;

    @NotNull
    @Positive
    private Integer duration;

    @ApiModelProperty(name = "startDate", required = true, value = "When the slide is visible", example = "2022-01-21")
    @DateTimeFormat(fallbackPatterns = {"yyyy-MM-dd"})
    private String startDate;

    @ApiModelProperty(name = "endDate", required = true, value = "When the slide is visible", example = "2022-02-21")
    @DateTimeFormat(fallbackPatterns = {"yyyy-MM-dd"})
    private String endDate;

    @ApiModelProperty(name = "startTime", required = true, value = "When the slide is visible", example = "10:45")
    @DateTimeFormat(fallbackPatterns = {"hh:mm"})
    private String startTime;

    @ApiModelProperty(name = "endTime", required = true, value = "When the slide is visible", example = "11:30")
    @DateTimeFormat(fallbackPatterns = {"hh:mm"})
    private String endTime;
}
