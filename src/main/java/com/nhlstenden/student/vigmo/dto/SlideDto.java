package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Generated
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

    @ApiModelProperty(name = "startDate", value = "When the slide is visible", example = "2022-01-21")
    @DateTimeFormat(fallbackPatterns = {"yyyy-MM-dd"})
    private String startDate;

    @ApiModelProperty(name = "endDate", value = "When the slide is visible", example = "2022-02-21")
    @DateTimeFormat(fallbackPatterns = {"yyyy-MM-dd"})
    private String endDate;

    @ApiModelProperty(name = "startTime", value = "When the slide is visible", example = "10:45")
    @DateTimeFormat(fallbackPatterns = {"hh:mm"})
    private String startTime;

    @ApiModelProperty(name = "endTime", value = "When the slide is visible", example = "11:30")
    @DateTimeFormat(fallbackPatterns = {"hh:mm"})
    private String endTime;
}
