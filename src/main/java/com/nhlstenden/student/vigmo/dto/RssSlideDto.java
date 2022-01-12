package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Generated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RssSlideDto extends SlideDto {
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
