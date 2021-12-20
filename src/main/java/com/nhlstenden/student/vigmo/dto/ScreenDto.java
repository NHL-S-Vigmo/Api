package com.nhlstenden.student.vigmo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenDto {
    private Long id;

    @Size(max = 50) @NotEmpty
    private String name;

    @Size(max = 50)
    private String location;

    @Size(max = 255) @NotEmpty
    private String authKey;
}
