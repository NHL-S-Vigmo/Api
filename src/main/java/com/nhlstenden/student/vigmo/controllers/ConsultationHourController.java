package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.ConsultationHourDto;
import com.nhlstenden.student.vigmo.security.JWTProvider;
import com.nhlstenden.student.vigmo.services.ConsultationHourService;
import com.nhlstenden.student.vigmo.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Consultation Hour Controller")
@RestController
@RequestMapping("consultation_hours")
public class ConsultationHourController extends AbstractVigmoController<ConsultationHourService, ConsultationHourDto> {

    public ConsultationHourController(ConsultationHourService service, JWTProvider jwtProvider, UserService userService) {
        super(service, jwtProvider, userService);
    }
}
