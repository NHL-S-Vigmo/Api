package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TextSlideDto extends SlideDto {
    @ApiModelProperty(name = "title", value = "Title of the slide")
    @NotEmpty
    private String title;

    @ApiModelProperty(name = "message", value = "Text of the slide")
    @NotEmpty
    private String message;
}
