package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.security.JWTProvider;
import com.nhlstenden.student.vigmo.services.RssSlideService;
import com.nhlstenden.student.vigmo.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Rss Slide Controller")
@RestController
@RequestMapping("rss_slides")
public class RssSlideController extends AbstractVigmoController<RssSlideService, RssSlideDto> {
    public RssSlideController(RssSlideService service, UserService userService) {
        super(service, userService);
    }
}
