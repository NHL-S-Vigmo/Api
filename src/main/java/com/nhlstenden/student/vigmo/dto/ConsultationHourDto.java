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
public class ConsultationHourDto {
    private Long id;
    private String description;
    private String weekDay;
    private LocalTime startTime;
    private LocalTime endTime;
}
