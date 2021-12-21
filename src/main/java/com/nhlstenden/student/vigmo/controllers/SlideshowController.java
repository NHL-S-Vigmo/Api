package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.dto.SlideshowDto;
import com.nhlstenden.student.vigmo.services.SlideshowService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Slideshow Controller")
@RestController
@RequestMapping("slideshows")
public class SlideshowController extends AbstractVigmoController<SlideshowService, SlideshowDto> {
    public SlideshowController(SlideshowService service) {
        super(service);
    }
}
