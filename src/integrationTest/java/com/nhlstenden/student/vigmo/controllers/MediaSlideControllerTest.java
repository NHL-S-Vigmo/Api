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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class MediaSlideControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        String getMediaSlidesJsonString = "[{\"id\":1,\"audioEnabled\":true,\"resource\":\"/videos/2021/2/christmas.mp4\",\"type\":\"video\",\"isActive\":true,\"duration\":60,\"startDate\":\"2021-12-20\",\"endDate\":\"2021-12-20\",\"startTime\":\"10:00\",\"endTime\":\"11:00\",\"slideshowId\":1},{\"id\":4,\"audioEnabled\":false,\"resource\":\"/videos/2021/3/exams.mkv\",\"type\":\"video\",\"isActive\":true,\"duration\":30,\"startDate\":null,\"endDate\":null,\"startTime\":null,\"endTime\":null,\"slideshowId\":2}]";

        this.mockMvc.perform(get("/media_slides")).andExpect(status().isOk()).andExpect(content().json(getMediaSlidesJsonString));
    }

    @Test
    public void testGetOne() throws  Exception {
        String getMediaSlideJsonString = "{\"id\":1,\"audioEnabled\":true,\"resource\":\"/videos/2021/2/christmas.mp4\",\"type\":\"video\",\"isActive\":true,\"duration\":60,\"startDate\":\"2021-12-20\",\"endDate\":\"2021-12-20\",\"startTime\":\"10:00\",\"endTime\":\"11:00\",\"slideshowId\":1}";

        this.mockMvc.perform(get("/media_slides/1")).andExpect(status().isOk()).andExpect(content().json(getMediaSlideJsonString));
        this.mockMvc.perform(get("/media_slides/2")).andExpect(status().isNotFound());
        this.mockMvc.perform(get("/media_slides/8")).andExpect(status().isNotFound());
    }

    @Test
    public void testPost() throws Exception {
        String providedJsonString = "{\"audioEnabled\":true,\"resource\":\"/videos/2021/2/test.mp4\",\"type\":\"video\",\"isActive\":true,\"duration\":80,\"startDate\":\"2021-12-24\",\"endDate\":\"2021-12-26\",\"startTime\":\"12:00\",\"endTime\":\"12:30\",\"slideshowId\":2}";
        String expectedJsonString = "{\"id\":8,\"audioEnabled\":true,\"resource\":\"/videos/2021/2/test.mp4\",\"type\":\"video\"},\"isActive\":true,\"duration\":80,\"startDate\":\"2021-12-24\",\"endDate\":\"2021-12-26\",\"startTime\":\"12:00\",\"endTime\":\"12:30\",\"slideshowId\":2}";

        this.mockMvc.perform(get("/media_slides/8")).andExpect(status().isNotFound());
        this.mockMvc.perform(post("/media_slides").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isCreated());
        this.mockMvc.perform(get("/media_slides/8")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
    }

    @Test
    public void testPut() throws Exception {
        String providedJsonString = "{\"audioEnabled\":true,\"resource\":\"/videos/2021/2/test.mp4\",\"type\":\"video\",\"isActive\":true,\"duration\":80,\"startDate\":\"2021-12-24\",\"endDate\":\"2021-12-26\",\"startTime\":\"12:00\",\"endTime\":\"12:30\",\"slideshowId\":2}}";
        String expectedJsonString = "{\"id\":1,\"audioEnabled\":true,\"resource\":\"/videos/2021/2/test.mp4\",\"type\":\"video\",\"isActive\":true,\"duration\":80,\"startDate\":\"2021-12-24\",\"endDate\":\"2021-12-26\",\"startTime\":\"12:00\",\"endTime\":\"12:30\",\"slideshowId\":2}}";

        this.mockMvc.perform(put("/media_slides/1").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isOk());
        this.mockMvc.perform(get("/media_slides/1")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
        this.mockMvc.perform(put("/media_slides/2").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());
        this.mockMvc.perform(put("/media_slides/8").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());

    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/media_slides/1")).andExpect(status().isOk());
        this.mockMvc.perform(delete("/media_slides/1")).andExpect(status().isNoContent());
        this.mockMvc.perform(get("/media_slides/1")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/media_slides/1")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/media_slides/2")).andExpect(status().isNotFound());
    }
}
