package com.nhlstenden.student.vigmo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Generated
@Getter
@Setter
@NoArgsConstructor
public class RssItemDto {
    public RssItemDto(String title, String description, String author, String category, String image) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
        this.image = image;
    }

    @ApiModelProperty(name = "title", value = "Rss title tag")
    @Size(max = 100)
    private String title;

    @ApiModelProperty(name = "description", value = "Rss description tag")
    @Size(max = 500)
    private String description;

    @ApiModelProperty(name = "author", value = "Rss author tag")
    @Size(max = 50)
    private String author;

    @ApiModelProperty(name = "category", value = "Rss category tag")
    @Size(max = 50)
    private String category;

    @ApiModelProperty(name = "image", value = "Rss image tag")
    @Size(max = 100)
    private String image;
}
