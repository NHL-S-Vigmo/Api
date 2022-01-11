package com.nhlstenden.student.vigmo.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDto {
    private String username;
    private String password;
    private String role;
    private boolean enabled;
}
