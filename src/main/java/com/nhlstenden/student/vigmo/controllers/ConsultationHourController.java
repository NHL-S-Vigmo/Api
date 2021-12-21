package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.dto.ConsultationHourDto;
import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.services.ConsultationHourService;
import com.nhlstenden.student.vigmo.services.ScreenService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Consultation Hour Controller")
@RestController
@RequestMapping("consultation_hour")
public class ConsultationHourController extends AbstractVigmoController<ConsultationHourService, ConsultationHourDto> {

    public ConsultationHourController(ConsultationHourService service) {
        super(service);
    }
}
