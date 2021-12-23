package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.TextSlideDto;
import com.nhlstenden.student.vigmo.services.TextSlideService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Text Slide Controller")
@RestController
@RequestMapping("text_slides")
public class TextSlideController extends AbstractVigmoController<TextSlideService, TextSlideDto> {
    public TextSlideController(TextSlideService service) {
        super(service);
    }
}

