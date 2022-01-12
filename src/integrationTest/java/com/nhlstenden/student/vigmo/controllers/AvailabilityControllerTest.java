package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class AvailabilityControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        String getAvailabilitiesJsonString = "[{\"id\":1,\"userId\":5,\"weekDay\":\"MONDAY\",\"startTime\":\"09:15\",\"endTime\":\"16:00\"},{\"id\":2,\"userId\":5,\"weekDay\":\"TUESDAY\",\"startTime\":\"10:15\",\"endTime\":\"11:00\"},{\"id\":3,\"userId\":5,\"weekDay\":\"TUESDAY\",\"startTime\":\"14:15\",\"endTime\":\"16:45\"},{\"id\":4,\"userId\":5,\"weekDay\":\"FRIDAY\",\"startTime\":\"10:15\",\"endTime\":\"11:00\"},{\"id\":5,\"userId\":2,\"weekDay\":\"MONDAY\",\"startTime\":\"11:15\",\"endTime\":\"13:00\"},{\"id\":6,\"userId\":2,\"weekDay\":\"THURSDAY\",\"startTime\":\"10:15\",\"endTime\":\"11:00\"},{\"id\":7,\"userId\":2,\"weekDay\":\"THURSDAY\",\"startTime\":\"14:15\",\"endTime\":\"16:45\"},{\"id\":8,\"userId\":2,\"weekDay\":\"FRIDAY\",\"startTime\":\"09:15\",\"endTime\":\"14:00\"},{\"id\":9,\"userId\":2,\"weekDay\":\"FRIDAY\",\"startTime\":\"15:15\",\"endTime\":\"17:00\"},{\"id\":10,\"userId\":4,\"weekDay\":\"MONDAY\",\"startTime\":\"10:15\",\"endTime\":\"14:00\"},{\"id\":11,\"userId\":1,\"weekDay\":\"FRIDAY\",\"startTime\":\"09:00\",\"endTime\":\"17:00\"}]";

        this.mockMvc.perform(get("/availabilities")).andExpect(status().isOk()).andExpect(content().json(getAvailabilitiesJsonString));
    }

    @Test
    public void testGetOne() throws  Exception {
        String getAvailabilityJsonString = "{\"id\":1,\"userId\":5,\"weekDay\":\"MONDAY\",\"startTime\":\"09:15\",\"endTime\":\"16:00\"}";

        this.mockMvc.perform(get("/availabilities/1")).andExpect(status().isOk()).andExpect(content().json(getAvailabilityJsonString));
        this.mockMvc.perform(get("/availabilities/12")).andExpect(status().isNotFound());
    }

    @Test
    public void testPost() throws Exception {
        String providedJsonString = "{\"userId\":1,\"weekDay\":\"THURSDAY\",\"startTime\":\"13:00\",\"endTime\":\"17:00\"}";
        String expectedJsonString = "{\"id\":12,\"userId\":1,\"weekDay\":\"THURSDAY\",\"startTime\":\"13:00\",\"endTime\":\"17:00\"}";

        AvailabilityDto dto = new AvailabilityDto(null, 1L, "", "", "");
        this.mockMvc.perform(get("/availabilities/12")).andExpect(status().isNotFound());
        this.mockMvc.perform(post("/availabilities").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto))).andExpect(status().isCreated());
        this.mockMvc.perform(get("/availabilities/12")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
    }

    @Test
    public void testPut() throws Exception {
        String providedJsonString = "{\"userId\":1,\"weekDay\":\"THURSDAY\",\"startTime\":\"13:00\",\"endTime\":\"17:00\"}";
        String expectedJsonString = "{\"id\":1,\"userId\":1,\"weekDay\":\"THURSDAY\",\"startTime\":\"13:00\",\"endTime\":\"17:00\"}";

        this.mockMvc.perform(put("/availabilities/1").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isOk());
        this.mockMvc.perform(get("/availabilities/1")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
        this.mockMvc.perform(put("/availabilities/12").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());

    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/availabilities/1")).andExpect(status().isOk());
        this.mockMvc.perform(delete("/availabilities/1")).andExpect(status().isNoContent());
        this.mockMvc.perform(get("/availabilities/1")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/availabilities/1")).andExpect(status().isNotFound());
    }
}
