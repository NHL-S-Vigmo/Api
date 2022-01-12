package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.security.JWTProvider;
import com.nhlstenden.student.vigmo.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("User Controller")
@RestController
@RequestMapping("users")
public class UserController extends AbstractVigmoController<UserService, UserDto> {
    public UserController(UserService service, JWTProvider jwtProvider) {
        super(service, jwtProvider, service);
    }
}

