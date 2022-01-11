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
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        String getScreensJsonString = "[{\"id\":1,\"username\":\"Thijs_Smegen\",\"password\":\"$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.\",\"enabled\":true,\"role\":\"ROLE_ADMIN\",\"pfpLocation\":\"/image_013.jpg\"},{\"id\":2,\"username\":\"Jan_Doornbos\",\"password\":\"$2y$10$qWUcTd1T1VG1M0Pmq839P.ziwvlJ0lBDEilcNEC7/TJluqEvuRLqW\",\"enabled\":false,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_014.png\"},{\"id\":3,\"username\":\"Niels_Doorn\",\"password\":\"$2y$10$sYG5WmMbT.u6TwDr/4cUb.OSVHpX8gRLK4BDNjzEpHHgt4kE6d.7O\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_015.gif\"},{\"id\":4,\"username\":\"Rene_Laan\",\"password\":\"$2y$10$l39MwIfSwvsHR4GTt2jm6enawKZzUkmS58l.yMn8hsM.CzvWcZtkq\",\"enabled\":false,\"role\":\"ROLE_ADMIN\",\"pfpLocation\":\"/image_016.png\"},{\"id\":5,\"username\":\"Martijn_Pomp\",\"password\":\"$2y$10$1N6lUFZ34frCZBw/MNM9s.i6DdzsdEM1llfjdbeLUP/akMVIO3tlC\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_017.png\"}]";

        this.mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(content().json(getScreensJsonString));
    }

    @Test
    public void testGetOne() throws  Exception {
        String getScreenJsonString = "{\"id\":1,\"username\":\"Thijs_Smegen\",\"password\":\"$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.\",\"enabled\":true,\"role\":\"ROLE_ADMIN\",\"pfpLocation\":\"/image_013.jpg\"}";

        this.mockMvc.perform(get("/users/1")).andExpect(status().isOk()).andExpect(content().json(getScreenJsonString));
        this.mockMvc.perform(get("/users/6")).andExpect(status().isNotFound());
    }

    @Test
    public void testPost() throws Exception {
        String providedJsonString = "{\"username\":\"Rob_Smit\",\"password\":\"$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_018.jpg\"}";
        String expectedJsonString = "{\"id\":6,\"username\":\"Rob_Smit\",\"password\":\"$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_018.jpg\"}";
        this.mockMvc.perform(get("/users/6")).andExpect(status().isNotFound());
        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isCreated());
        this.mockMvc.perform(get("/users/6")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
    }

    @Test
    public void testPut() throws Exception {
        String providedJsonString = "{\"username\":\"Rob_Smit\",\"password\":\"$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_018.jpg\"}";
        String expectedJsonString = "{\"id\":1,\"username\":\"Rob_Smit\",\"password\":\"$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.\",\"enabled\":true,\"role\":\"ROLE_TEACHER\",\"pfpLocation\":\"/image_018.jpg\"}";

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
}
