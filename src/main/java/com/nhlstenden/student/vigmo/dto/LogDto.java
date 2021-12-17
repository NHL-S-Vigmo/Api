package com.nhlstenden.student.vigmo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDto {
    private Long id;
    private Long userId;
    private String username;
    private String action;
    private String message;
    private Instant datetime;
}
