package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(name = "username", value = "Unique username of the user.")
    @Size(max = 50)
    @NotEmpty
    private String username;

    @ApiModelProperty(name = "password", value = "The users password, empty on GET requests. Leave empty on POST and PUT to not change the password.")
    private String password;

    @ApiModelProperty(name = "enabled", value = "If the account can sign in or not.")
    @NotNull
    private Boolean enabled;

    @ApiModelProperty(name = "role", value = "Role of the user")
    @Size(max = 50)
    @NotEmpty
    private String role;

    @ApiModelProperty(name = "pfpLocation", value = "Location of the profile picture")
    @Size(max = 220)
    private String pfpLocation;
}
