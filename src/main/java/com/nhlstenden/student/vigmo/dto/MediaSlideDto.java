package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaSlideDto extends SlideDto {
    @ApiModelProperty(name = "audioEnabled", value = "If the current media slide should play audio")
    @NotNull
    private Boolean audioEnabled;
    @ApiModelProperty(name = "type", value = "The media type the slide uses")
    @NotNull
    @Max(50)
    private String type;
    @ApiModelProperty(name = "resource", value = "The url the resource is located")
    @NotNull
    @Max(255)
    private String resource;
}
