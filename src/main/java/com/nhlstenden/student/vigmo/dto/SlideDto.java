package com.nhlstenden.student.vigmo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalTime startTime;
    private LocalTime endTime;
}
