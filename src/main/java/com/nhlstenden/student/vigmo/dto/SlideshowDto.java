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
public class SlideshowDto {
    private Long id;

    @Max(20)
    @ApiModelProperty(name = "screenId", value = "Id of the screen the slideshow is assigned to")
    private Long screenId;

    @NotNull
    @ApiModelProperty(name = "name", value = "Name of the slideshow")
    @Max(50)
    private String name;
}
