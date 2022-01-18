package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Api("Rss Slide Controller")
@RestController
@RequestMapping("rss_slides")
public class RssSlideController extends AbstractVigmoController<RssSlideService, RssSlideDto> {
    public RssSlideController(RssSlideService service, UserService userService) {
        super(service, userService);
    }

    @ApiOperation(value = "Gets the latest rss item from the requested rss feed")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Invalid RssSlide id")})
    @GetMapping("/{id}/render")
    ResponseEntity<String> renderFile(@PathVariable("id") final long id) throws URISyntaxException, IOException, InterruptedException, SAXException, ParserConfigurationException {
        RssSlideDto rssSlide = service.get(id);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(rssSlide.getUrl()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        // parse XML file
        DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
        //FIXME: doesn't parse
        Document doc = db.parse(response.body());
        NodeList list = doc.getElementsByTagName("item");



        return ResponseEntity.ok()
                .body("");
    }
}
