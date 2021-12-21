package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.services.AvailabilityService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Availability Controller")
@RestController
@RequestMapping("availabilities")
public class AvailabilityController extends AbstractVigmoController<AvailabilityService, AvailabilityDto> {
    public AvailabilityController(AvailabilityService service) {
        super(service);
    }
}
