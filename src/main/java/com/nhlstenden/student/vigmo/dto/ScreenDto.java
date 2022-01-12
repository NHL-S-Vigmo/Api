package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Generated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenDto {
    private Long id;

    @ApiModelProperty(notes = "Name of the screen", name = "name", required = true, value = "Groot Mededelingen bord")
    @Size(max = 50)
    @NotEmpty
    private String name;

    @ApiModelProperty(notes = "Location of the screen", name = "location", value = "Boven aan de trap")
    @Size(max = 50)
    private String location;

    @ApiModelProperty(notes = "Authentication key of the screen", name = "authKey", required = true, value = "random-string-with-enough-length")
    @Size(min = 20, max = 255)
    @NotEmpty
    private String authKey;
}
