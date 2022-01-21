package com.nhlstenden.student.vigmo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Generated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDto {
    public LogDto(Long userId, String username, String action, String message, Long datetime) {
        this.userId = userId;
        this.username = username;
        this.action = action;
        this.message = message;
        this.datetime = datetime;
    }

    private Long id;

    @Positive
    private Long userId;

    @NotEmpty @Size(max = 50)
    private String username;

    @NotEmpty @Size(max = 50)
    private String action;

    @Size(max = 220)
    private String message;

    private Long datetime;
}
