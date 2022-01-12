package com.nhlstenden.student.vigmo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
