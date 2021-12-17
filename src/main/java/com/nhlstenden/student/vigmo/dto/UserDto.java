package com.nhlstenden.student.vigmo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @Size(max = 50) @NotEmpty
    private String username;

    @Size(max = 220) @NotEmpty
    private String password;

    private Boolean enabled;

    @Size(max = 50) @NotEmpty
    private String role;

    @Size(max = 220)
    private String pfpLocation;
}
