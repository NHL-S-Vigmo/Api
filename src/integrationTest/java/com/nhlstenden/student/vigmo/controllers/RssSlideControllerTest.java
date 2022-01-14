package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.dto.ConsultationHourDto;
import com.nhlstenden.student.vigmo.dto.MediaSlideDto;
import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class RssSlideControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private HashMap<Integer, RssSlideDto> testDataMap;
    private ObjectMapper om;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
        testDataMap = new HashMap<>();
        testDataMap.put(1, new RssSlideDto( 2L,"https://www.nu.nl/rss/Algemeen","title","description","creator","category","enclosure",true,30,"2021-12-21",null,"12:00",null,1L));
        testDataMap.put(2, new RssSlideDto( 7L,"http://feeds.nos.nl/nosnieuwsalgemeen","title","description",null,null,null,true,30,null,"2021-12-21",null,null,2L));

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetAll() throws Exception {
        List<RssSlideDto> testDataList = new ArrayList<>(testDataMap.values());

        this.mockMvc.perform(get("/rss_slides")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataList)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetOne() throws  Exception {

        this.mockMvc.perform(get("/rss_slides/2")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataMap.get(1))));
        this.mockMvc.perform(get("/rss_slides/1")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(get("/rss_slides/8")).
                andExpect(status().
                        isNotFound());

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPost() throws Exception {
        RssSlideDto providedDto = new RssSlideDto( null,"https://www.telegraaf.nl/nieuws/rss","title","description",null,"category","enclosure",true,30,"2021-12-21",null,"12:00",null,1L);

        MvcResult result = this.mockMvc.perform(post("/rss_slides").
                        contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isCreated()).andReturn();
        int location = Integer.parseInt(Objects.requireNonNull(result.getResponse()
                        .getHeader("Location"))
                .replace("/rss_slides/", ""));
        RssSlideDto expectedDto = new RssSlideDto( (long) location,"https://www.telegraaf.nl/nieuws/rss","title","description",null,"category","enclosure",true,30,"2021-12-21",null,"12:00",null,1L);

        this.mockMvc.perform(get("/rss_slides/" + location)).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPut() throws Exception {
        RssSlideDto providedDto = new RssSlideDto( null,"https://www.telegraaf.nl/nieuws/rss","title","description",null,"category","enclosure",true,30,"2021-12-21",null,"12:00",null,1L);
        RssSlideDto expectedDto = new RssSlideDto( 2L,"https://www.telegraaf.nl/nieuws/rss","title","description",null,"category","enclosure",true,30,"2021-12-21",null,"12:00",null,1L);

        this.mockMvc.perform(put("/rss_slides/2").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isOk());
        this.mockMvc.perform(get("/rss_slides/2")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
        this.mockMvc.perform(put("/rss_slides/1").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(put("/rss_slides/4").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testDelete() throws Exception {
        this.mockMvc.perform(delete("/rss_slides/2")).
                andExpect(status().
                        isNoContent());
        this.mockMvc.perform(get("/rss_slides/2")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(delete("/rss_slides/2")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(delete("/rss_slides/1")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testUserValidation() throws Exception {
        RssSlideDto nonExistentSlideshowDto = new RssSlideDto( null,"https://www.telegraaf.nl/nieuws/rss","title","description",null,"category","enclosure",true,30,"2021-12-21",null,"12:00",null,4L);
        this.mockMvc.perform(post("/rss_slides").
                        contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(nonExistentSlideshowDto))).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(put("/rss_slides/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(nonExistentSlideshowDto))).
                andExpect(status().
                        isNotFound());
    }
}
