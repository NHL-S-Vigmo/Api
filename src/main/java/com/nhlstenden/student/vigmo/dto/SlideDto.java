package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
    private Long slideshowId;
    private Boolean isActive;
    private Integer duration;

    private LocalDate startDate;
    private LocalDate endDate;

    @ApiModelProperty(name = "startTime", required = true, value = "When the hour starts", example = "10:45:00")
    @NotNull
    private String startTime;

    @ApiModelProperty(name = "endTime", required = true, value = "When the hour ends", example = "11:30:00")
    @NotNull
    private String endTime;
}
