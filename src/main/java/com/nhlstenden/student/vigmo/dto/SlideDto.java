package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class SlideDto {
    private Long id;

    @Positive
    @NotNull
    @Max(20)
    private Long slideshowId;

    @NotNull
    private Boolean isActive;

    @Max(11)
    @NotNull
    @Positive
    private Integer duration;

    private LocalDate startDate;

    private LocalDate endDate;

    @ApiModelProperty(name = "startTime", required = true, value = "When the hour starts", example = "10:45:00")
    @DateTimeFormat(fallbackPatterns = {"hh:mm:ss", "hh:mm"})
    private String startTime;

    @ApiModelProperty(name = "endTime", required = true, value = "When the hour ends", example = "11:30:00")
    @DateTimeFormat(fallbackPatterns = {"hh:mm:ss", "hh:mm"})
    private String endTime;
}
