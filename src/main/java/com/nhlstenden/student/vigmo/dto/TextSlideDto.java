package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Generated
@Getter
@Setter
@NoArgsConstructor
public class TextSlideDto extends SlideDto {
    @ApiModelProperty(name = "title", value = "Title of the slide")
    @NotEmpty
    private String title;

    @ApiModelProperty(name = "message", value = "Text of the slide")
    @NotEmpty
    private String message;
}
