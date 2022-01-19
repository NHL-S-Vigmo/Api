package com.nhlstenden.student.vigmo.dto;

import com.nhlstenden.student.vigmo.validators.Base64Validator;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Generated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private Long id;

    @ApiModelProperty(name = "File Name", value = "The name and extension of the file.")
    @Size(max = 220)
    @NotEmpty
    private String name;

    @ApiModelProperty(name = "MIME Type", value = "MIME Type/Media Type of the file")
    @Size(max = 100)
    @NotEmpty
    private String mimeType;

    @ApiModelProperty(name = "File Key", value = "Base64 representation of your file.")
    @Size(min = 10)
    @NotEmpty
    @Base64Validator
    private String data;

    @ApiModelProperty(name = "File Key", value = "Should be left empty, this is generated on the server. Use this to request a rendered file.")
    private String key;
}
