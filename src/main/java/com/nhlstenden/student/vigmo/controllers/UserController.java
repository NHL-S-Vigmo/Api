package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("User Controller")
@RestController
@RequestMapping("users")
public class UserController extends AbstractVigmoController<UserService, UserDto> {
    public UserController(UserService service) {
        super(service, service);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public ResponseEntity<Void> post(UserDto postObject, Authentication authentication) {
        return super.post(postObject, authentication);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> delete(long id, Authentication authentication) {
        return super.delete(id, authentication);
    }
}

