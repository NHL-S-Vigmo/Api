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
public class ScreenDto {
    private Long id;

    @ApiModelProperty(notes = "Name of the screen", name = "name", required = true, value = "Groot Mededelingen bord")
    @Size(max = 50)
    @NotEmpty
    private String name;

    @ApiModelProperty(notes = "Location of the screen", name = "location", value = "Boven aan de trap")
    @Size(max = 50)
    private String location;

    @ApiModelProperty(notes = "Authentication key of the screen", name = "authKey", value = "Should be left empty, it will be generated serverside eitherway.")
    private String authKey;
}
