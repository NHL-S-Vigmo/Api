package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.dto.TextSlideDto;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class TextSlideControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private HashMap<Integer, TextSlideDto> testDataMap;
    private ObjectMapper om;


    @BeforeEach
    public void setup() throws FileNotFoundException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
        testDataMap = new HashMap<>();

        File file = new File("src/test/resources/text_slide_large_text_test.txt");
        Scanner fileScanner = new Scanner(file);
        String largeText = fileScanner.nextLine();

        testDataMap.put(1, new TextSlideDto(3L,"Title","Message",1L,true,30,null,"2021-12-19",null,"12:00"));
        testDataMap.put(2, new TextSlideDto(5L,"Corona update","Beste student, Tijdens de persconferentie van zaterdag 18 december heeft het kabinet een harde lockdown aangekondigd om de verspreiding van de omikronvariant van het coronavirus af te remmen.",2L,true,30,"2022-01-01",null,null,null));
        testDataMap.put(3, new TextSlideDto(6L,"text of 7500 characters",largeText,2L,false,30,null,"2021-12-21",null,null));


    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetAll() throws Exception {

        List<TextSlideDto> testDataList = new ArrayList<>(testDataMap.values());
        this.mockMvc.perform(get("/text_slides")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataList)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetOne() throws  Exception {

        this.mockMvc.perform(get("/text_slides/3")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataMap.get(1))));
        this.mockMvc.perform(get("/text_slides/1")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(get("/text_slides/8")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPost() throws Exception {
        TextSlideDto providedDto = new TextSlideDto(null,"Title","Message123",1L,true,30,null,"2021-12-19",null,"12:00");
        TextSlideDto expectedDto = new TextSlideDto(8L,"Title","Message123",1L,true,30,null,"2021-12-19",null,"12:00");

        this.mockMvc.perform(get("/text_slides/8")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(post("/text_slides").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isCreated());
        this.mockMvc.perform(get("/text_slides/8")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPut() throws Exception {
        TextSlideDto providedDto = new TextSlideDto(null,"Title","Message123",1L,true,30,null,"2021-12-19",null,"12:00");
        TextSlideDto expectedDto = new TextSlideDto(3L,"Title","Message123",1L,true,30,null,"2021-12-19",null,"12:00");

        this.mockMvc.perform(put("/text_slides/3").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isOk());
        this.mockMvc.perform(get("/text_slides/3")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
        this.mockMvc.perform(put("/text_slides/8").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(put("/text_slides/1").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/text_slides/3")).
                andExpect(status().
                        isOk());
        this.mockMvc.perform(delete("/text_slides/3")).
                andExpect(status().
                        isNoContent());
        this.mockMvc.perform(get("/text_slides/3")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(delete("/text_slides/3")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(delete("/text_slides/1")).
                andExpect(status().
                        isNotFound());

    }
}
