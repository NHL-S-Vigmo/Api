package com.nhlstenden.student.vigmo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RssSlideDto extends SlideDto {
    @Size(max = 220) @NotEmpty
    private String url;

    @Size(max = 50)
    private String titleTag;

    @Size(max = 50)
    private String descriptionTag;

    @Size(max = 50)
    private String authorTag;

    @Size(max = 50)
    private String categoryTag;

    @Size(max = 50)
    private String imageTag;
}
