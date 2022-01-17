package com.nhlstenden.student.vigmo.dto;

import lombok.*;

import java.time.Instant;

@Generated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDto {
    public LogDto(Long userId, String username, String action, String message, Instant datetime) {
        this.userId = userId;
        this.username = username;
        this.action = action;
        this.message = message;
        this.datetime = datetime;
    }

    private Long id;
    private Long userId;
    private String username;
    private String action;
    private String message;
    private Instant datetime;
}
