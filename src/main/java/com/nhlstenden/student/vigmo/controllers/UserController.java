package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("User Controller")
@RestController
@RequestMapping("users")
public class UserController extends AbstractVigmoController<UserService, UserDto> {
    public UserController(UserService service) {
        super(service);
    }

    @Override
    public ResponseEntity<Void> post(UserDto postObject) {
        //todo: modify the postObject to encrypt the password, then call the super
        return super.post(postObject);
    }

    @Override
    public ResponseEntity<Void> put(long id, UserDto putObject) {
        //todo: modify the putObject to encrypt the password, then call the super
        return super.put(id, putObject);
    }
}

