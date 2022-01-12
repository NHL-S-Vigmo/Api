package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class SlideshowControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        String getScreensJsonString = "[{\"id\":1,\"screenId\":3,\"name\":\"Christmas\"},{\"id\":2,\"screenId\":2,\"name\":\"Period 2 2021\"},{\"id\":3,\"screenId\":1,\"name\":\"Period 3 2021\"}]";

        this.mockMvc.perform(get("/slideshows")).andExpect(status().isOk()).andExpect(content().json(getScreensJsonString));
    }

    @Test
    public void testGetOne() throws  Exception {
        String getScreenJsonString = "{\"id\":1,\"screenId\":3,\"name\":\"Christmas\"}";

        this.mockMvc.perform(get("/slideshows/1")).andExpect(status().isOk()).andExpect(content().json(getScreenJsonString));
        this.mockMvc.perform(get("/slideshows/4")).andExpect(status().isNotFound());
    }

    @Test
    public void testPost() throws Exception {
        String providedJsonString = "{\"screenId\":3,\"name\":\"New Years\"}";
        String expectedJsonString = "{\"id\":4,\"screenId\":3,\"name\":\"New Years\"}";

        this.mockMvc.perform(get("/slideshows/4")).andExpect(status().isNotFound());
        this.mockMvc.perform(post("/slideshows").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isCreated());
        this.mockMvc.perform(get("/slideshows/4")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
    }

    @Test
    public void testPut() throws Exception {
        String providedJsonString = "{\"screenId\":3,\"name\":\"New Years\"}";
        String expectedJsonString = "{\"id\":1,\"screenId\":3,\"name\":\"New Years\"}";

        this.mockMvc.perform(put("/slideshows/1").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isOk());
        this.mockMvc.perform(get("/slideshows/1")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
        this.mockMvc.perform(put("/slideshows/4").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());

    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/slideshows/1")).andExpect(status().isOk());
        this.mockMvc.perform(delete("/slideshows/1")).andExpect(status().isNoContent());
        this.mockMvc.perform(get("/slideshows/1")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/slideshows/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testScreenValidation() throws Exception {
        String duplicateScreenJsonString = "{\"screenId\":3,\"name\":\"New Years\"}";
        String nonExistentScreenJsonString = "{\"screenId\":4,\"name\":\"New Years\"}";
        this.mockMvc.perform(post("/slideshows").contentType(MediaType.APPLICATION_JSON).content(duplicateScreenJsonString)).andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/slideshows").contentType(MediaType.APPLICATION_JSON).content(nonExistentScreenJsonString)).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetSlideshowVariables(){

    }

    @Test
    public void testGetSlideshowSlides() throws Exception {
        this.mockMvc.perform(get("/slideshows/1/slides"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.length()").value(3)
                );
    }
}
