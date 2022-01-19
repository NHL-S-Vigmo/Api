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
    @GetMapping("/{id}/render")
    ResponseEntity<RssItemDto> renderFile(@PathVariable("id") final long id) throws URISyntaxException, IOException, InterruptedException, SAXException, ParserConfigurationException {
        RssSlideDto rssSlide = service.get(id);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(rssSlide.getUrl()))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
        InputSource inStream = new InputSource();
        inStream.setCharacterStream(new StringReader(response.body()));
        Document doc = db.parse(inStream);
        NodeList list = doc.getElementsByTagName("item");
        Node node = list.item(0);
        ArrayList<String> latestNews = new ArrayList<String>();
        RssItemDto rssItemDto = new RssItemDto();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            //TODO: Check if tag exsist
            //TODO: Check if tag is not null

            NodeList titleNode = element.getElementsByTagName(rssSlide.getTitleTag());
            if (titleNode.getLength() > 0) {
                rssItemDto.setTitle(titleNode.item(0).getTextContent());
            }

            NodeList descriptionNode = element.getElementsByTagName(rssSlide.getDescriptionTag());
            if (descriptionNode.getLength() > 0) {
                rssItemDto.setDescription(descriptionNode.item(0).getTextContent());
            }

            NodeList categoryNode = element.getElementsByTagName(rssSlide.getCategoryTag());
            if (categoryNode.getLength() > 0) {
                rssItemDto.setCategory(categoryNode.item(0).getTextContent());
            }

            NodeList authorNode = element.getElementsByTagName(rssSlide.getAuthorTag());
            if (authorNode.getLength() > 0) {
                rssItemDto.setAuthor(authorNode.item(0).getTextContent());
            }

            NodeList imageNode = element.getElementsByTagName(rssSlide.getImageTag());
            if (imageNode.getLength() > 0) {
//                if(imageNode.item(0).getAttributes().getNamedItem("zieketest")) {
//                    getAttributes().getNamedItem("url")
//                }
//                .getNodeValue()
                //TODO: check if attribute exist.
                imageNode.item(0).getAttributes().getNamedItem("zieketest");
                rssItemDto.setImage(imageNode.item(0).getAttributes().getNamedItem("url").getNodeValue());
            }

        }

        return ResponseEntity.ok()
                .body(rssItemDto);
    }
}
