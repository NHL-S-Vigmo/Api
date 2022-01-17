package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.SlideshowDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class SlideshowControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private HashMap<Integer, SlideshowDto> testDataMap;
    private ObjectMapper om;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
        testDataMap = new HashMap<>();

        testDataMap.put(1, new SlideshowDto(1L,3L,"Christmas"));
        testDataMap.put(2, new SlideshowDto(2L,2L,"Period 2 2021"));
        testDataMap.put(3, new SlideshowDto(3L,1L,"Period 3 2021"));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetAll() throws Exception {

        List<SlideshowDto> testDataList = new ArrayList<>(testDataMap.values());

        this.mockMvc.perform(get("/slideshows")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataList)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetOne() throws  Exception {
        String getScreenDto = "{\"id\":1,\"screenId\":3,\"name\":\"Christmas\"}";

        //try to get a slideshow with id 1
        this.mockMvc.perform(get("/slideshows/1")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(getScreenDto));

        //try to get a slideshow with a non-existent id
        this.mockMvc.perform(get("/slideshows/9999")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPost() throws Exception {
        SlideshowDto providedDto = new SlideshowDto(null,3L,"New Years");
        SlideshowDto expectedDto = new SlideshowDto(4L,3L,"New Years");

        // try to create a slideshow and check if the location header is present.
        this.mockMvc.perform(post("/slideshows").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isCreated())
                .andExpect(header()
                        .exists("Location"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPut() throws Exception {
        SlideshowDto providedDto = new SlideshowDto(null,3L,"New Years");

        //first try to put on a existing object
        this.mockMvc.perform(put("/slideshows/1").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isOk());

        //try to put on a non-existent id
        this.mockMvc.perform(put("/slideshows/9999").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testDelete() throws Exception {

        //try to delete an existing slideshow object
        this.mockMvc.perform(delete("/slideshows/1")).
                andExpect(status().
                        isNoContent());

        //try to delete a non-existent slideshow object
        this.mockMvc.perform(delete("/slideshows/999")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testScreenExistsCheck() throws Exception {
        SlideshowDto nonExistentScreenDto = new SlideshowDto(null,500L,"New Years");

        //First test if the screen can be found using a non-existent screen id:
        this.mockMvc.perform(post("/slideshows").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(nonExistentScreenDto))).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetSlideshowVariables() throws Exception {

        // get the variables for the first slideshow
        this.mockMvc.perform(get("/slideshows/1/variables"))
                .andExpect(status().
                        isOk())
                .andExpectAll(
                        jsonPath("$.length()").
                                value(2)
                );
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetSlideshowSlides() throws Exception {
        this.mockMvc.perform(get("/slideshows/1/slides"))
                .andExpect(status().
                        isOk())
                .andExpectAll(
                        jsonPath("$.length()").
                                value(3)
                );
    }
}
