package com.nhlstenden.student.vigmo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenDto {
    private Long id;
    private String name;
    private String location;
    private String authKey;
}
