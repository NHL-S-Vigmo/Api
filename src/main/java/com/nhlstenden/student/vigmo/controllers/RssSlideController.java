package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.RssItemDto;
import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.services.RssSlideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Rss Slide Controller")
@RestController
@RequestMapping("rss_slides")
public class RssSlideController extends AbstractVigmoController<RssSlideService, RssSlideDto> {
    public RssSlideController(RssSlideService service) {
        super(service);
    }

    @ApiOperation(value = "Gets the latest rss item from the requested rss feed")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Invalid RssSlide id")})
    @GetMapping("/{id}/latest")
    ResponseEntity<RssItemDto> latestFeedItem(@PathVariable("id") final long id) {
        //get the current rss slide info
        RssItemDto rssSlide = service.getLatestFeedItem(id);

        return ResponseEntity.ok()
                .body(rssSlide);
    }
}
