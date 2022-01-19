package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.RssItemDto;
import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.services.RssSlideService;
import com.nhlstenden.student.vigmo.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.validator.internal.xml.XmlParserHelper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Api("Rss Slide Controller")
@RestController
@RequestMapping("rss_slides")
public class RssSlideController extends AbstractVigmoController<RssSlideService, RssSlideDto> {
    public RssSlideController(RssSlideService service, UserService userService) {
        super(service, userService);
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
