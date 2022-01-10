package com.nhlstenden.student.vigmo.dto;

import com.nhlstenden.student.vigmo.validators.BcryptValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @Size(max = 50) @NotEmpty
    private String username;

    @BcryptValidator
    private String password;

    @NotNull
    private Boolean enabled;

    @Size(max = 50) @NotEmpty
    private String role;

    @Size(max = 220)
    private String pfpLocation;
}
