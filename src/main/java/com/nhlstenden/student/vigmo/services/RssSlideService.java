package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.RssItemDto;
import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.RssSlide;
import com.nhlstenden.student.vigmo.repositories.RssSlideRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import com.nhlstenden.student.vigmo.transformers.XmlUtility;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RssSlideService extends AbstractVigmoService<RssSlideRepository, RssSlideDto, RssSlide> {
    private final SlideshowService slideshowService;

    public RssSlideService(RssSlideRepository repo, MappingUtility mapper, LogService logService, SlideshowService slideshowService) {
        super(repo, mapper, RssSlideDto.class, RssSlide.class, logService);
        this.slideshowService = slideshowService;
    }

    @Override
    public long create(RssSlideDto rssSlideDto) {
        //Will throw a data not found runtime exception if slideshow does not exist
        slideshowService.get(rssSlideDto.getSlideshowId());
        return super.create(rssSlideDto);
    }

    @Override
    public void update(RssSlideDto rssSlideDto, long id) {
        //Will throw a data not found runtime exception if slideshow does not exist
        slideshowService.get(rssSlideDto.getSlideshowId());
        super.update(rssSlideDto, id);
    }

    public RssItemDto getLatestFeedItem(long id) {
        RssSlideDto slide = this.get(id);

        try {
            //create the http client to get the rss feed.
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(slide.getUrl()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // initialize the xml document tools
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();

            //parse the response from the RSS server.
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(response.body()));
            Document doc = db.parse(inStream);

            //get all the item tags
            NodeList list = doc.getElementsByTagName("item");

            //get the first item (should be the most recent)
            Node node = list.item(0);

            RssItemDto rssItemDto = new RssItemDto();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                List<Node> nodes = XmlUtility.asList(node.getChildNodes());

                //get the title tag.
                if (slide.getTitleTag() != null) {
                    Optional<Node> tag = nodes
                            .stream().filter(n -> n.getNodeName().equals(slide.getTitleTag())).findFirst();
                    tag.ifPresent(value -> rssItemDto.setTitle(value.getTextContent()));
                }

                //get the description tag.
                if (slide.getDescriptionTag() != null) {
                    Optional<Node> tag = nodes
                            .stream().filter(n -> n.getNodeName().equals(slide.getDescriptionTag())).findFirst();
                    tag.ifPresent(value -> rssItemDto.setDescription(value.getTextContent()));
                }

                //get the category tag.
                if (slide.getCategoryTag() != null) {
                    String tag = nodes
                            .stream().filter(n -> n.getNodeName().equals(slide.getCategoryTag()))
                            .map(Node::getTextContent).collect(Collectors.joining(", "));
                    rssItemDto.setCategory(tag);
                }

                //get the author tag.
                if (slide.getAuthorTag() != null) {
                    String tag = nodes
                            .stream().filter(n -> n.getNodeName().equals(slide.getAuthorTag()))
                            .map(Node::getTextContent).collect(Collectors.joining(", "));
                    rssItemDto.setAuthor(tag);
                }

                //get the image tag.
                if (slide.getImageTag() != null) {
                    Optional<Node> tag = nodes
                            .stream().filter(n -> n.getNodeName().equals(slide.getImageTag())).findFirst();
                    tag.ifPresent(value -> rssItemDto.setImage(value.getAttributes().getNamedItem("url").getNodeValue()));
                }
            }

            return rssItemDto;
        } catch (URISyntaxException | IOException | InterruptedException | SAXException | ParserConfigurationException e) {
            throw new DataNotFoundException("Problem rendering the RSS Feed, check configuration");
        }
    }
}
