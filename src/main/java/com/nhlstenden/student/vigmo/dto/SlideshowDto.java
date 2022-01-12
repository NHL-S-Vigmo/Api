package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Generated
@Getter
@Setter
@NoArgsConstructor
public class SlideshowDto {
    private Long id;

    @ApiModelProperty(name = "screenId", value = "Id of the screen the slideshow is assigned to")
    private Long screenId;

    @NotEmpty
    @Size(max = 50)
    @ApiModelProperty(name = "name", value = "Name of the slideshow")
    private String name;
}
