package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.LogDto;
import com.nhlstenden.student.vigmo.services.LogService;
import com.nhlstenden.student.vigmo.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Log Controller")
@RestController
@RequestMapping("logs")
public class LogController extends AbstractVigmoController<LogService, LogDto> {
    public LogController(LogService service, UserService userService) {
        super(service, userService);
    }
}
