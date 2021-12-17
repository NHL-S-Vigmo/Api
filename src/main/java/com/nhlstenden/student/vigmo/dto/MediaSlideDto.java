package com.nhlstenden.student.vigmo.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaSlideDto extends SlideDto {
    private Boolean audioEnabled;
    private String type;
    private String resource;
}
