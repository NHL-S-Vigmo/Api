package com.nhlstenden.student.vigmo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlideshowSlidesDto {
    private Long slideshowId;
    private Boolean isActive;
    private Integer duration;
    private String path; // /media_slides
}
