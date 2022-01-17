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
public class SlideshowVariableDto {
    private Long id;

    @NotNull
    @ApiModelProperty(name = "slideshowId", value = "Id of the slideshow")
    private Long slideshowId;

    @NotEmpty
    @Size(max = 50)
    @ApiModelProperty(name = "name", value = "Name of the variable")
    private String name;

    @NotEmpty
    @Size(max = 220)
    @ApiModelProperty(name = "value", value = "Value of the variable")
    private String value;
}
