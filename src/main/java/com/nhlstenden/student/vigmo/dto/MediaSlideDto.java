package com.nhlstenden.student.vigmo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaSlideDto extends SlideDto {
    private Boolean audioEnabled;
    private String type;
    private String resource;
}
