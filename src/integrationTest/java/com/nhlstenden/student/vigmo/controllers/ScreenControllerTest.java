package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class ScreenControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        String getScreensJsonString = "[{\"id\":1,\"name\":\"Screen_103\",\"location\":\"Upstairs south wall\",\"authKey\":\"DMIrM5V5A8dt7QwJ9jk9Q9By4s1351jI\"},{\"id\":2,\"name\":\"Screen_105\",\"location\":\"Marketing department\",\"authKey\":\"YLXvpg8uweU67ezdphj6XDuVCwY2g5v6\"},{\"id\":3,\"name\":\"Screen_109\",\"location\":\"Cafeteria\",\"authKey\":\"4XFZbFUeHU50Gk2LIn96ZNpn2CYP3KrG\"}]";

        this.mockMvc.perform(get("/screens")).andExpect(status().isOk()).andExpect(content().json(getScreensJsonString));
    }

    @Test
    public void testGetOne() throws  Exception {
        String getScreenJsonString = "{\"id\":1,\"name\":\"Screen_103\",\"location\":\"Upstairs south wall\",\"authKey\":\"DMIrM5V5A8dt7QwJ9jk9Q9By4s1351jI\"}";

        this.mockMvc.perform(get("/screens/1")).andExpect(status().isOk()).andExpect(content().json(getScreenJsonString));
        this.mockMvc.perform(get("/screens/4")).andExpect(status().isNotFound());
    }

    @Test
    public void testPost() throws Exception {
        String providedJsonString = "{\"name\":\"Screen_110\",\"location\":\"Upstairs north wall\",\"authKey\":\"iGlGqaKCPNmd2PmAUlzrRkFPIwpLfxva\"}";
        String expectedJsonString = "{\"id\":4,\"name\":\"Screen_110\",\"location\":\"Upstairs north wall\",\"authKey\":\"iGlGqaKCPNmd2PmAUlzrRkFPIwpLfxva\"}";

        this.mockMvc.perform(get("/screens/4")).andExpect(status().isNotFound());
        this.mockMvc.perform(post("/screens").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isCreated());
        this.mockMvc.perform(get("/screens/4")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
    }

    @Test
    public void testPut() throws Exception {
        String providedJsonString = "{\"name\":\"Screen_110\",\"location\":\"Upstairs north wall\",\"authKey\":\"iGlGqaKCPNmd2PmAUlzrRkFPIwpLfxva\"}";
        String expectedJsonString = "{\"id\":1,\"name\":\"Screen_110\",\"location\":\"Upstairs north wall\",\"authKey\":\"iGlGqaKCPNmd2PmAUlzrRkFPIwpLfxva\"}";

        this.mockMvc.perform(put("/screens/1").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isOk());
        this.mockMvc.perform(get("/screens/1")).andExpect(status().isOk()).andExpect(content().json(expectedJsonString));
        this.mockMvc.perform(put("/screens/4").contentType(MediaType.APPLICATION_JSON).content(providedJsonString)).andExpect(status().isNotFound());

    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/screens/1")).andExpect(status().isOk());
        this.mockMvc.perform(delete("/screens/1")).andExpect(status().isNoContent());
        this.mockMvc.perform(get("/screens/1")).andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/screens/1")).andExpect(status().isNotFound());
    }
}
