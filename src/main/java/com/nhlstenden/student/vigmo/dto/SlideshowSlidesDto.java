package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Generated
@Getter
@Setter
@NoArgsConstructor
public class SlideshowSlidesDto {
    private Long slideId;

    @ApiModelProperty(name = "isActive", value = "If the slide should be rendered")
    private Boolean isActive;

    @ApiModelProperty(name = "duration", value = "How long the slide should be visible in seconds.")
    private Integer duration;

    @ApiModelProperty(name = "path", value = "Path where the slide can be found.")
    private String path; // /media_slides
}
