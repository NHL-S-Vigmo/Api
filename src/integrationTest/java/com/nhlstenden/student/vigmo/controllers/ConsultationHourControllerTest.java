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
public class ConsultationHourControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        String getConsultationHoursJsonString = "[{\"id\":1,\"weekDay\":\"MONDAY\",\"startTime\":\"12:15\",\"endTime\":\"13:00\"},{\"id\":2,\"weekDay\":\"TUESDAY\",\"startTime\":\"10:15\",\"endTime\":\"11:00\"},{\"id\":3,\"weekDay\":\"TUESDAY\",\"startTime\":\"14:15\",\"endTime\":\"14:45\"}]";

        this.mockMvc.perform(get("/consultation_hours")).andExpect(status().isOk()).andExpect(content().json(getConsultationHoursJsonString));
    }

    @Test
    public void testGetOne() throws Exception {
        String getConsultationHourJsonString = "{\"id\":1,\"weekDay\":\"MONDAY\",\"startTime\":\"12:15\",\"endTime\":\"13:00\"}";

        this.mockMvc.perform(get("/consultation_hours/1")).andExpect(status().isOk()).andExpect(content().json(getConsultationHourJsonString));
        this.mockMvc.perform(get("/consultation_hours/4")).andExpect(status().isNotFound());
    }

    @Test
    public void testPost() throws Exception {
        String providedJsonString = "{\"weekDay\":\"WEDNESDAY\",\"startTime\":\"11:15\",\"endTime\":\"13:00\"}";
        String expectedJsonString = "{\"id\":4,\"weekDay\":\"WEDNESDAY\",\"startTime\":\"11:15\",\"endTime\":\"13:00\"}";

        this.mockMvc.perform(get("/consultation_hours/4")).andExpect(status().isNotFound());
        this.mockMvc.perform(post("/consultation_hours").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isCreated());
        this.mockMvc.perform(get("/consultation_hours/4")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
    }

    @Test
    public void testPut() throws Exception {
        String providedJsonString = "{\"weekDay\":\"WEDNESDAY\",\"startTime\":\"11:15\",\"endTime\":\"13:00\"}";
        String expectedJsonString = "{\"id\":1,\"weekDay\":\"WEDNESDAY\",\"startTime\":\"11:15\",\"endTime\":\"13:00\"}";

        this.mockMvc.perform(put("/consultation_hours/1").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isOk());
        this.mockMvc.perform(get("/consultation_hours/1")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
        this.mockMvc.perform(put("/consultation_hours/4").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());

    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/consultation_hours/1")).andExpect(status().isOk());
        this.mockMvc.perform(delete("/consultation_hours/1")).andExpect(status().isNoContent());
        this.mockMvc.perform(get("/consultation_hours/1")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/consultation_hours/1")).andExpect(status().isNotFound());
    }
}
