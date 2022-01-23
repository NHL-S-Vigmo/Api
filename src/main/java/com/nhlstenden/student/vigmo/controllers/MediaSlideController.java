package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.MediaSlideDto;
import com.nhlstenden.student.vigmo.services.MediaSlideService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Media Slide Controller")
@RestController
@RequestMapping("media_slides")
public class MediaSlideController extends AbstractVigmoController<MediaSlideService, MediaSlideDto> {
    public MediaSlideController(MediaSlideService service) {
        super(service);
    }
}
