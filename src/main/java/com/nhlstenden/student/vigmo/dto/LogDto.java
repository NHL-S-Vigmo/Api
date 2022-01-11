package com.nhlstenden.student.vigmo.dto;

import lombok.*;

import java.time.Instant;

@Generated
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
