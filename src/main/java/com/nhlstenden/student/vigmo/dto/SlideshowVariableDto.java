package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlideshowVariableDto {
    private Long id;

    @NotNull
    @Max(20)
    @ApiModelProperty(name = "slideshowId", value = "Id of the slideshow")
    private Long slideshowId;

    @NotNull
    @Max(50)
    @ApiModelProperty(name = "name", value = "Name of the variable")
    private String name;

    @NotNull
    @Max(220)
    @ApiModelProperty(name = "value", value = "Value of the variable")
    private String value;
}
