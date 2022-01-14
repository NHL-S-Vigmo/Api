package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.dto.ConsultationHourDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class ConsultationHourControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private HashMap<Integer, ConsultationHourDto> testDataMap;
    private ObjectMapper om;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
        testDataMap = new HashMap<>();

        testDataMap.put(1, new ConsultationHourDto(1L,"Noon consultation","MONDAY","12:15","13:00"));
        testDataMap.put(2, new ConsultationHourDto(2L,"Morning consultation","TUESDAY","10:15","11:00"));
        testDataMap.put(3, new ConsultationHourDto(3L,"Afternoon consultation","TUESDAY","14:15","14:45"));

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetAll() throws Exception {
        List<ConsultationHourDto> testDataList = new ArrayList<>(testDataMap.values());
        this.mockMvc.perform(get("/consultation_hours")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataList)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetOne() throws  Exception {
        this.mockMvc.perform(get("/consultation_hours/1")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataMap.get(1))));
        this.mockMvc.perform(get("/consultation_hours/4")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPost() throws Exception {
        ConsultationHourDto providedDto = new ConsultationHourDto(null,"Afternoon consultation","WEDNESDAY","13:15","14:45");
        ConsultationHourDto expectedDto = new ConsultationHourDto(4L,"Afternoon consultation","WEDNESDAY","13:15","14:45");

        this.mockMvc.perform(post("/consultation_hours").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isCreated());
        this.mockMvc.perform(get("/consultation_hours/4")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPut() throws Exception {
        ConsultationHourDto providedDto = new ConsultationHourDto(null,"Afternoon consultation","WEDNESDAY","13:15","14:45");
        ConsultationHourDto expectedDto = new ConsultationHourDto(1L,"Afternoon consultation","WEDNESDAY","13:15","14:45");

        this.mockMvc.perform(put("/consultation_hours/1").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isOk());
        this.mockMvc.perform(get("/consultation_hours/1")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
        this.mockMvc.perform(put("/consultation_hours/4").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/consultation_hours/1")).
                andExpect(status().
                        isOk());
        this.mockMvc.perform(delete("/consultation_hours/1")).
                andExpect(status().
                        isNoContent());
        this.mockMvc.perform(get("/consultation_hours/1")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(delete("/consultation_hours/1")).
                andExpect(status().
                        isNotFound());
    }
}
