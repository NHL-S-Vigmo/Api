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
public class SlideshowDto {
    private Long id;

    @ApiModelProperty(name = "screenId", value = "Id of the screen the slideshow is assigned to")
    private Long screenId;

    @NotEmpty
    @Size(max = 50)
    @ApiModelProperty(name = "name", value = "Name of the slideshow")
    private String name;
}
