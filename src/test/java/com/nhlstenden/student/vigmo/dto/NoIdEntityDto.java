package com.nhlstenden.student.vigmo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class used to test handling of a dto without id field by the AbstractVigmoService
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoIdEntityDto {
    private Long notAnId;
}
