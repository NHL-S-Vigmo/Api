package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Generated
@Getter
@Setter
@NoArgsConstructor
public class RssSlideDto extends SlideDto {
    public RssSlideDto(Long id, String url, String titleTag,
                       String descriptionTag, String authorTag, String categoryTag,
                       String imageTag, @NotNull Boolean isActive,
                       @NotNull @Positive Integer duration,
                       String startDate, String endDate, String startTime,
                       String endTime, @Positive @NotNull Long slideshowId) {
        super(id, slideshowId, isActive, duration, startDate, endDate, startTime, endTime);
        this.url = url;
        this.titleTag = titleTag;
        this.descriptionTag = descriptionTag;
        this.authorTag = authorTag;
        this.categoryTag = categoryTag;
        this.imageTag = imageTag;
    }

    @ApiModelProperty(name = "url", value = "Url of the RSS")
    @Size(max = 220)
    @NotEmpty
    private String url;

    @ApiModelProperty(name = "titleTag", value = "Rss tag where the title is located")
    @Size(max = 50)
    private String titleTag;

    @ApiModelProperty(name = "descriptionTag", value = "Rss tag where the description is located")
    @Size(max = 50)
    private String descriptionTag;

    @ApiModelProperty(name = "authorTag", value = "Rss tag where the author is located")
    @Size(max = 50)
    private String authorTag;

    @ApiModelProperty(name = "categoryTag", value = "Rss tag where the category is located")
    @Size(max = 50)
    private String categoryTag;

    @ApiModelProperty(name = "imageTag", value = "Rss tag where the image location is located")
    @Size(max = 50)
    private String imageTag;
}
