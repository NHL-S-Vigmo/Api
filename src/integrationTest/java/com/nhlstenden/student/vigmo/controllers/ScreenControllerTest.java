package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.ScreenDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class ScreenControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ScreenDto providerData103;
    private ScreenDto providerData105;
    private ScreenDto providerData109;
    private ScreenDto providerData110;
    private ObjectMapper om;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();

        //data objects currently in DB
        providerData103 = new ScreenDto(1L, "Screen_103", "Upstairs south wall", "DMIrM5V5A8dt7QwJ9jk9Q9By4s1351jI");
        providerData105 = new ScreenDto(2L, "Screen_105", "Marketing department", "YLXvpg8uweU67ezdphj6XDuVCwY2g5v6");
        providerData109 = new ScreenDto(3L, "Screen_109", "Cafeteria", "4XFZbFUeHU50Gk2LIn96ZNpn2CYP3KrG");
        providerData110 = new ScreenDto(4L, "Screen_110", "Upstairs north wall", "iGlGqaKCPNmd2PmAUlzrRkFPIwpLfxva");
    }

    @Test
    @WithMockUser(username = "bacon", authorities = "ROLE_DOCENT")
    public void testGetAll() throws Exception {
        List<ScreenDto> screens = new ArrayList<>();
        screens.add(providerData103);
        screens.add(providerData105);
        screens.add(providerData109);

        this.mockMvc.perform(get("/screens"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(screens)));
    }

    @Test
    @WithMockUser(username = "bacon", authorities = "ROLE_DOCENT")
    public void testGetOne() throws Exception {
        this.mockMvc.perform(get("/screens/1"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(providerData103)));

        this.mockMvc.perform(get("/screens/4"))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "bacon", authorities = "ROLE_DOCENT")
    public void testPost() throws Exception {
        this.mockMvc.perform(get("/screens/4"))
                .andExpect(status()
                        .isNotFound());

        //set the object id to null
        Long id = providerData110.getId();
        providerData110.setId(null);

        this.mockMvc.perform(post("/screens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(providerData110)))
                .andExpect(status()
                        .isCreated());

        //set the object id back to what was expected
        providerData110.setId(id);

        this.mockMvc.perform(get("/screens/4"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(providerData110)));
    }

    @Test
    @WithMockUser(username = "bacon", authorities = "ROLE_DOCENT")
    public void testPut() throws Exception {
        this.mockMvc.perform(put("/screens/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(providerData103)))
                .andExpect(status()
                        .isOk());

        this.mockMvc.perform(get("/screens/1"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(providerData103)));

        this.mockMvc.perform(put("/screens/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(providerData110)))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "bacon", authorities = "ROLE_DOCENT")
    public void testDelete() throws Exception {
        this.mockMvc.perform(get("/screens/1"))
                .andExpect(status().isOk());
        this.mockMvc.perform(delete("/screens/1"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/screens/1"))
                .andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/screens/1"))
                .andExpect(status().isNotFound());
    }
}
