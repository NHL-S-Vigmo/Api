package com.nhlstenden.student.vigmo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Generated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @Size(max = 50)
    @NotEmpty
    private String username;

    private String password;

    @NotNull
    private Boolean enabled;

    @Size(max = 50)
    @NotEmpty
    private String role;

    @Size(max = 220)
    private String pfpLocation;
}
