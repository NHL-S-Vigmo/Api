package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Generated
@Getter
@Setter
@NoArgsConstructor
public class TextSlideDto extends SlideDto {
    public TextSlideDto(Long id, String title, String message,
                        @Positive @NotNull Long slideshowId,
                        @NotNull Boolean isActive, @NotNull @Positive Integer duration,
                        String startDate, String endDate, String startTime, String endTime) {
        super(id, slideshowId, isActive, duration, startDate, endDate, startTime, endTime);
        this.title = title;
        this.message = message;
    }

    @ApiModelProperty(name = "title", value = "Title of the slide")
    @NotEmpty
    private String title;

    @ApiModelProperty(name = "message", value = "Text of the slide")
    @NotEmpty
    private String message;
}
