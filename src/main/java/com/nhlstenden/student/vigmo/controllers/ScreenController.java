package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.services.ScreenService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Screen Controller")
@RestController
@RequestMapping("screens")
public class ScreenController extends AbstractVigmoController<ScreenService, ScreenDto> {
    public ScreenController(ScreenService service) {
        super(service);
    }
}
