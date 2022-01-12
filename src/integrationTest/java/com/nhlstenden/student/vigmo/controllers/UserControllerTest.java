package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.UserDto;
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
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper om;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.om = new ObjectMapper();
    }

    @Test
    public void testGetAll() throws Exception {
        String getScreensJsonString = "[{\"id\":1,\"username\":\"Thijs_Smegen\",\"password\":\"\",\"enabled\":true,\"role\":\"ROLE_ADMIN\",\"pfpLocation\":\"/image_013.jpg\"},{\"id\":2,\"username\":\"Jan_Doornbos\",\"password\":\"\",\"enabled\":false,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_014.png\"},{\"id\":3,\"username\":\"Niels_Doorn\",\"password\":\"\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_015.gif\"},{\"id\":4,\"username\":\"Rene_Laan\",\"password\":\"\",\"enabled\":false,\"role\":\"ROLE_ADMIN\",\"pfpLocation\":\"/image_016.png\"},{\"id\":5,\"username\":\"Martijn_Pomp\",\"password\":\"\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_017.png\"}]";

        this.mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(content().json(getScreensJsonString));
    }

    @Test
    public void testGetOne() throws Exception {
        String getScreenJsonString = "{\"id\":1,\"username\":\"Thijs_Smegen\",\"password\":\"\",\"enabled\":true,\"role\":\"ROLE_ADMIN\",\"pfpLocation\":\"/image_013.jpg\"}";

        this.mockMvc.perform(get("/users/1")).andExpect(status().isOk()).andExpect(content().json(getScreenJsonString));
        this.mockMvc.perform(get("/users/6")).andExpect(status().isNotFound());
    }

    @Test
    public void testPost() throws Exception {
        String providedJsonString = "{\"username\":\"Rob_Smit\",\"password\":\"\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_018.jpg\"}";
        String expectedJsonString = "{\"id\":6,\"username\":\"Rob_Smit\",\"password\":\"\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_018.jpg\"}";
        this.mockMvc.perform(get("/users/6")).andExpect(status().isNotFound());
        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isCreated());
        this.mockMvc.perform(get("/users/6")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
    }

    @Test
    public void testPut() throws Exception {
        String providedJsonString = "{\"username\":\"Rob_Smit\",\"password\":\"\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_018.jpg\"}";
        String expectedJsonString = "{\"id\":1,\"username\":\"Rob_Smit\",\"password\":\"\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_018.jpg\"}";

        this.mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isOk());
        this.mockMvc.perform(get("/users/1")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
        this.mockMvc.perform(put("/users/6").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());

    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/users/1")).andExpect(status().isOk());
        this.mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
        this.mockMvc.perform(get("/users/1")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/users/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testCreateExistingUsername() throws Exception {
        UserDto user = new UserDto(null, "Thijs_Smegen", "password", true, "ROLE_DOCENT", "");

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(user)))
                .andExpect(status()
                        .isConflict());
    }

    @Test
    public void changePasswordUsingPut() throws Exception {
        UserDto user = new UserDto(1L, "Thijs_Smegen", "password1", true, "ROLE_ADMIN", "");

        this.mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(user)))
                .andExpect(status()
                        .isOk());
    }
}
