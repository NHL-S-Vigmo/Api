package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Generated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaSlideDto extends SlideDto {
    @ApiModelProperty(name = "audioEnabled", value = "If the current media slide should play audio")
    @NotNull
    private Boolean audioEnabled;

    @ApiModelProperty(name = "type", value = "The media type the slide uses")
    @NotEmpty
    @Size(max = 50)
    private String type;

    @ApiModelProperty(name = "resource", value = "The url the resource is located")
    @NotEmpty
    @Size(max = 255)
    private String resource;
}
