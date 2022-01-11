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

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class RssSlideControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        String getScreensJsonString = "[{\"id\":2,\"url\":\"https://www.nu.nl/rss/Algemeen\",\"titleTag\":\"title\",\"descriptionTag\":\"description\",\"authorTag\":\"creator\",\"categoryTag\":\"category\",\"imageTag\":\"enclosure\",\"isActive\":true,\"duration\":30,\"startDate\":\"2021-12-21\",\"endDate\":null,\"startTime\":\"12:00\",\"endTime\":null,\"slideshowId\":1},{\"id\":7,\"url\":\"http://feeds.nos.nl/nosnieuwsalgemeen\",\"titleTag\":\"title\",\"descriptionTag\":\"description\",\"authorTag\":null,\"categoryTag\":null,\"imageTag\":null,\"isActive\":true,\"duration\":30,\"startDate\":null,\"endDate\":\"2021-12-21\",\"startTime\":null,\"endTime\":null,\"slideshowId\":2}]";

        this.mockMvc.perform(get("/rss_slides")).andExpect(status().isOk()).andExpect(content().json(getScreensJsonString));
    }

    @Test
    public void testGetOne() throws  Exception {
        String getScreenJsonString = "{\"id\":2,\"url\":\"https://www.nu.nl/rss/Algemeen\",\"titleTag\":\"title\",\"descriptionTag\":\"description\",\"authorTag\":\"creator\",\"categoryTag\":\"category\",\"imageTag\":\"enclosure\",\"isActive\":true,\"duration\":30,\"startDate\":\"2021-12-21\",\"endDate\":null,\"startTime\":\"12:00\",\"endTime\":null,\"slideshowId\":1}";

        this.mockMvc.perform(get("/rss_slides/2")).andExpect(status().isOk()).andExpect(content().json(getScreenJsonString));
        this.mockMvc.perform(get("/rss_slides/1")).andExpect(status().isNotFound());
        this.mockMvc.perform(get("/rss_slides/8")).andExpect(status().isNotFound());

    }

    @Test
    public void testPost() throws Exception {
        String providedJsonString = "{\"url\":\"https://www.telegraaf.nl/nieuws/rss\",\"titleTag\":\"title\",\"descriptionTag\":\"description\",\"authorTag\":\"\",\"categoryTag\":\"category\",\"imageTag\":\"enclosure\",\"isActive\":true,\"duration\":30,\"startDate\":\"2021-12-21\",\"endDate\":\"\",\"startTime\":\"12:00\",\"endTime\":\"\",\"slideshowId\":1}";
        String expectedJsonString = "{\"id\":8,\"url\":\"https://www.telegraaf.nl/nieuws/rss\",\"titleTag\":\"title\",\"descriptionTag\":\"description\",\"authorTag\":null,\"categoryTag\":\"category\",\"imageTag\":\"enclosure\",\"isActive\":true,\"duration\":30,\"startDate\":\"2021-12-21\",\"endDate\":null,\"startTime\":\"12:00\",\"endTime\":null,\"slideshowId\":1}";

        this.mockMvc.perform(get("/rss_slides/8")).andExpect(status().isNotFound());
        this.mockMvc.perform(post("/rss_slides").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isCreated());
        this.mockMvc.perform(get("/rss_slides/8")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
    }

    @Test
    public void testPut() throws Exception {
        String providedJsonString = "{\"url\":\"https://www.telegraaf.nl/nieuws/rss\",\"titleTag\":\"title\",\"descriptionTag\":\"description\",\"authorTag\":\"\",\"categoryTag\":\"category\",\"imageTag\":\"enclosure\",\"isActive\":true,\"duration\":30,\"startDate\":\"2021-12-21\",\"endDate\":\"\",\"startTime\":\"12:00\",\"endTime\":\"\",\"slideshowId\":1}";
        String expectedJsonString = "{\"id\":2,\"url\":\"https://www.telegraaf.nl/nieuws/rss\",\"titleTag\":\"title\",\"descriptionTag\":\"description\",\"authorTag\":null,\"categoryTag\":\"category\",\"imageTag\":\"enclosure\",\"isActive\":true,\"duration\":30,\"startDate\":\"2021-12-21\",\"endDate\":null,\"startTime\":\"12:00\",\"endTime\":null,\"slideshowId\":1}";

        this.mockMvc.perform(put("/rss_slides/2").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isOk());
        this.mockMvc.perform(get("/rss_slides/2")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
        this.mockMvc.perform(put("/rss_slides/1").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());
        this.mockMvc.perform(put("/rss_slides/4").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());

    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/rss_slides/2")).andExpect(status().isOk());
        this.mockMvc.perform(delete("/rss_slides/2")).andExpect(status().isNoContent());
        this.mockMvc.perform(get("/rss_slides/2")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/rss_slides/2")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/rss_slides/1")).andExpect(status().isNotFound());

    }
}
