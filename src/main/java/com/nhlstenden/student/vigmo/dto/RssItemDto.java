package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Generated
@Getter
@Setter
@NoArgsConstructor
public class RssItemDto {

    @ApiModelProperty(name = "title", value = "Rss title tag")
    private String title;

    @ApiModelProperty(name = "description", value = "Rss description tag")
    private String description;

    @ApiModelProperty(name = "author", value = "Rss author tag")
    private String author;

    @ApiModelProperty(name = "category", value = "Rss category tag")
    private String category;

    @ApiModelProperty(name = "image", value = "Rss image tag")
    private String image;
}
