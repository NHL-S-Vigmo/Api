package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
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

    @ApiModelProperty(name = "userId", value = "Direct link to the user table, to have a connection if user changes their username after malicious actions.")
    private Long userId;

    @ApiModelProperty(name = "username", value = "Username of the user that performed the action, added to persist logs when users are deleted.")
    @NotEmpty @Size(max = 50)
    private String username;

    @ApiModelProperty(name = "action", value = "The event that took place")
    @NotEmpty @Size(max = 50)
    private String action;

    @ApiModelProperty(name = "message", value = "Description of the action.")
    @Size(max = 220)
    private String message;

    @ApiModelProperty(name = "datetime", value = "Timestamp of the event")
    private Long datetime;
}
