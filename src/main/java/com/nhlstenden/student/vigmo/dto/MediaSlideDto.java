package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;

@Generated
@Getter
@Setter
@NoArgsConstructor
public class MediaSlideDto extends SlideDto {
    public MediaSlideDto(Long id, Boolean audioEnabled, String type,
                         String resource, @NotNull Boolean isActive,
                         @NotNull @Positive Integer duration,
                         String startDate, String endDate,
                         String startTime, String endTime,
                         @Positive @NotNull Long slideshowId) {
        super(id, slideshowId, isActive, duration, startDate, endDate, startTime, endTime);
        this.audioEnabled = audioEnabled;
        this.type = type;
        this.resource = resource;
    }

    @ApiModelProperty(name = "audioEnabled", value = "If the current media slide should play audio")
    @NotNull
    private Boolean audioEnabled;

    @ApiModelProperty(name = "type", value = "The media type the slide uses")
    @NotEmpty
    @Size(max = 100)
    private String type;

    @ApiModelProperty(name = "resource", value = "The url the resource is located")
    @NotEmpty
    @Size(max = 255)
    private String resource;
}
