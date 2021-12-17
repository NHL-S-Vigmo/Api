package com.nhlstenden.student.vigmo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {
    private Long id;
    private Long userId;
    private String weekDay;
    private LocalTime startTime;
    private LocalTime endTime;
}
