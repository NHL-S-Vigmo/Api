package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.services.SlideshowVariableService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Slideshow Variable Controller")
@RestController
@RequestMapping("slideshow_variables")
public class SlideshowVariableController extends AbstractVigmoController<SlideshowVariableService, SlideshowVariableDto> {
    public SlideshowVariableController(SlideshowVariableService service) {
        super(service);
    }
}
