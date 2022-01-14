package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.TextSlideDto;
import com.nhlstenden.student.vigmo.dto.UserDto;
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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private HashMap<Integer, UserDto> testDataMap;
    private ObjectMapper om;


    @BeforeEach
    public void setup() throws FileNotFoundException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
        testDataMap = new HashMap<>();

        testDataMap.put(1, new UserDto(1L,"Thijs_Smegen","",true,"ROLE_ADMIN","/image_013.jpg"));
        testDataMap.put(2, new UserDto(2L,"Jan_Doornbos","",false,"ROLE_TEACHER","/image_014.png"));
        testDataMap.put(3, new UserDto(3L,"Niels_Doorn","",true,"ROLE_TEACHER","/image_015.gif"));
        testDataMap.put(4, new UserDto(4L,"Rene_Laan","",false,"ROLE_ADMIN","/image_016.png"));
        testDataMap.put(5, new UserDto(5L,"Martijn_Pomp","",true,"ROLE_TEACHER","/image_017.png"));

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetAll() throws Exception {

        List<UserDto> testDataList = new ArrayList<>(testDataMap.values());
        this.mockMvc.perform(get("/users")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataList)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetOne() throws Exception {

        this.mockMvc.perform(get("/users/1")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataMap.get(1))));
        this.mockMvc.perform(get("/users/6")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPost() throws Exception {
        UserDto providedDto = new UserDto(null,"Rob_Smit","$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.",true,"ROLE_TEACHER","/image_018.jpg");
        UserDto expectedDto = new UserDto(6L,"Rob_Smit","",true,"ROLE_TEACHER","/image_018.jpg");
        this.mockMvc.perform(get("/users/6")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(post("/users").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isCreated());
        this.mockMvc.perform(get("/users/6")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPut() throws Exception {
        UserDto providedDto = new UserDto(null,"Rob_Smit","$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.",true,"ROLE_TEACHER","/image_018.jpg");
        UserDto expectedDto = new UserDto(1L,"Rob_Smit","",true,"ROLE_TEACHER","/image_018.jpg");

        this.mockMvc.perform(put("/users/1").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isOk());
        this.mockMvc.perform(get("/users/1")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
        this.mockMvc.perform(put("/users/6").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testDelete() throws Exception {
        this.mockMvc.perform(delete("/users/1")).
                andExpect(status().
                        isNoContent());
        this.mockMvc.perform(get("/users/1")).
                andExpect(status().
                        isNotFound());

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testDeleteNonExistentUser() throws Exception {
        this.mockMvc.perform(delete("/users/10")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testCreateExistingUsername() throws Exception {
        UserDto user = new UserDto(null, "Thijs_Smegen", "password", true, "ROLE_DOCENT", "");

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(user)))
                .andExpect(status()
                        .isConflict());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void changePasswordUsingPut() throws Exception {
        UserDto user = new UserDto(1L, "Thijs_Smegen", "password1", true, "ROLE_ADMIN", "");

        this.mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(user)))
                .andExpect(status()
                        .isOk());
    }
}
