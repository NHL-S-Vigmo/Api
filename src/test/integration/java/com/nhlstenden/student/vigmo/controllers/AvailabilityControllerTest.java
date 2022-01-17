package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
public class AvailabilityControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private HashMap<Integer, AvailabilityDto> testDataMap;
    private ObjectMapper om;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
        testDataMap = new HashMap<>();
        testDataMap.put(1, new AvailabilityDto(1L, 5L, "MONDAY", "09:15", "16:00"));
        testDataMap.put(2, new AvailabilityDto(2L, 5L, "TUESDAY", "10:15", "11:00"));
        testDataMap.put(3, new AvailabilityDto(3L, 5L, "TUESDAY", "14:15", "16:45"));
        testDataMap.put(4, new AvailabilityDto(4L, 5L, "FRIDAY", "10:15", "11:00"));
        testDataMap.put(5, new AvailabilityDto(5L, 2L, "MONDAY", "11:15", "13:00"));
        testDataMap.put(6, new AvailabilityDto(6L, 2L, "THURSDAY", "10:15", "11:00"));
        testDataMap.put(7, new AvailabilityDto(7L, 2L, "THURSDAY", "14:15", "16:45"));
        testDataMap.put(8, new AvailabilityDto(8L, 2L, "FRIDAY", "09:15", "14:00"));
        testDataMap.put(9, new AvailabilityDto(9L, 2L, "FRIDAY", "15:15", "17:00"));
        testDataMap.put(10, new AvailabilityDto(10L, 4L, "MONDAY", "10:15", "14:00"));
        testDataMap.put(11, new AvailabilityDto(11L, 1L, "FRIDAY", "09:00", "17:00"));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetAll() throws Exception {

        List<AvailabilityDto> testDataList = new ArrayList<>(testDataMap.values());
        this.mockMvc.perform(get("/availabilities"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(testDataList)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetOne() throws Exception {
        this.mockMvc.perform(get("/availabilities/1"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(testDataMap.get(1))));
        this.mockMvc.perform(get("/availabilities/12"))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPost() throws Exception {
        AvailabilityDto providedDto = new AvailabilityDto(null, 1L, "THURSDAY", "13:00", "17:00");
        AvailabilityDto expectedDto = new AvailabilityDto(12L, 1L, "THURSDAY", "13:00", "17:00");

        this.mockMvc.perform(post("/availabilities")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isCreated());
        this.mockMvc.perform(get("/availabilities/12")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPut() throws Exception {
        AvailabilityDto providedDto = new AvailabilityDto(null, 1L, "THURSDAY", "13:00", "17:00");
        AvailabilityDto expectedDto = new AvailabilityDto(1L, 1L, "THURSDAY", "13:00", "17:00");

        this.mockMvc.perform(put("/availabilities/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isOk());
        this.mockMvc.perform(get("/availabilities/1")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
        this.mockMvc.perform(put("/availabilities/12").
                        contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testDelete() throws Exception {
        //try deleting an existing availability
        this.mockMvc.perform(delete("/availabilities/1")).
                andExpect(status().
                        isNoContent());

        //try deleting a non-existent availability
        this.mockMvc.perform(delete("/availabilities/999")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testUserValidation() throws Exception {
        AvailabilityDto nonExistentUserDto = new AvailabilityDto(null, 999L, "MONDAY", "10:00", "10:00");
        //test creating a record with an invalid user id
        this.mockMvc.perform(post("/availabilities").
                        contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(nonExistentUserDto))).
                andExpect(status().
                        isNotFound());

        //test updating a record with an invalid user id
        this.mockMvc.perform(put("/availabilities/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(nonExistentUserDto))).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testModelValidation() throws Exception {
        AvailabilityDto providedDto = new AvailabilityDto(null, 1L, "THUDAY", "13:a0", "12-08-1999");

        //test if the model is invalid
        this.mockMvc.perform(post("/availabilities")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(providedDto)))
                .andExpect(status().
                        isBadRequest())
                .andExpectAll(
                        jsonPath("$.weekDay").exists(),
                        jsonPath("$.startTime").exists(),
                        jsonPath("$.endTime").exists()
                );
    }
}
