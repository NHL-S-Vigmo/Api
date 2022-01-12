package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class TextSlideControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        String getScreensJsonString = "[{\"id\":3,\"title\":\"Title\",\"message\":\"Message\",\"isActive\":true,\"duration\":30,\"startDate\":null,\"endDate\":\"2021-12-19\",\"startTime\":null,\"endTime\":\"12:00\",\"slideshowId\":1},{\"id\":5,\"title\":\"Corona update\",\"isActive\":true,\"duration\":30,\"startDate\":\"2022-01-01\",\"endDate\":null,\"startTime\":null,\"endTime\":null,\"slideshowId\":2},{\"id\":6,\"title\":\"text of 7500 characters\",\"isActive\":false,\"duration\":30,\"startDate\":null,\"endDate\":\"2021-12-21\",\"startTime\":null,\"endTime\":null,\"slideshowId\":2}]";

        this.mockMvc.perform(get("/text_slides")).andExpect(status().isOk()).andExpect(content().json(getScreensJsonString));
    }

    @Test
    public void testGetOne() throws  Exception {
        String getScreenJsonString = "{\"id\":3,\"title\":\"Title\",\"message\":\"Message\",\"isActive\":true,\"duration\":30,\"startDate\":null,\"endDate\":\"2021-12-19\",\"startTime\":null,\"endTime\":\"12:00\",\"slideshowId\":1}";

        this.mockMvc.perform(get("/text_slides/3")).andExpect(status().isOk()).andExpect(content().json(getScreenJsonString));
        this.mockMvc.perform(get("/text_slides/1")).andExpect(status().isNotFound());
        this.mockMvc.perform(get("/text_slides/8")).andExpect(status().isNotFound());
    }

    @Test
    public void testPost() throws Exception {
        String providedJsonString = "{\"title\":\"Title\",\"message\":\"Message123\",\"isActive\":true,\"duration\":30,\"startDate\":null,\"endDate\":\"2021-12-19\",\"startTime\":null,\"endTime\":\"12:00\",\"slideshowId\":1}";
        String expectedJsonString = "{\"id\":8,\"title\":\"Title\",\"message\":\"Message123\",\"isActive\":true,\"duration\":30,\"startDate\":null,\"endDate\":\"2021-12-19\",\"startTime\":null,\"endTime\":\"12:00\",\"slideshowId\":1}";

        this.mockMvc.perform(get("/text_slides/8")).andExpect(status().isNotFound());
        this.mockMvc.perform(post("/text_slides").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isCreated());
        this.mockMvc.perform(get("/text_slides/8")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
    }

    @Test
    public void testPut() throws Exception {
        String providedJsonString = "{\"title\":\"Title\",\"message\":\"Message123\",\"isActive\":true,\"duration\":30,\"startDate\":null,\"endDate\":\"2021-12-19\",\"startTime\":null,\"endTime\":\"12:00\",\"slideshowId\":1}";
        String expectedJsonString = "{\"id\":8,\"title\":\"Title\",\"message\":\"Message123\",\"isActive\":true,\"duration\":30,\"startDate\":null,\"endDate\":\"2021-12-19\",\"startTime\":null,\"endTime\":\"12:00\",\"slideshowId\":1}";

        this.mockMvc.perform(put("/text_slides/3").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isOk());
        this.mockMvc.perform(get("/text_slides/3")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
        this.mockMvc.perform(put("/text_slides/8").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());
        this.mockMvc.perform(put("/text_slides/1").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());

    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/text_slides/3")).andExpect(status().isOk());
        this.mockMvc.perform(delete("/text_slides/3")).andExpect(status().isNoContent());
        this.mockMvc.perform(get("/text_slides/3")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/text_slides/3")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/text_slides/1")).andExpect(status().isNotFound());

    }
}
