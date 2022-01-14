package com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.dto.ScreenDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<Integer, ScreenDto> testDataMap;
    private ObjectMapper om;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
        testDataMap = new HashMap<>();

        //data objects currently in DB
         testDataMap.put(1, new ScreenDto(1L, "Screen_103", "Upstairs south wall", "DMIrM5V5A8dt7QwJ9jk9Q9By4s1351jI"));
         testDataMap.put(2, new ScreenDto(2L, "Screen_105", "Marketing department", "YLXvpg8uweU67ezdphj6XDuVCwY2g5v6"));
         testDataMap.put(3, new ScreenDto(3L, "Screen_109", "Cafeteria", "4XFZbFUeHU50Gk2LIn96ZNpn2CYP3KrG"));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetAll() throws Exception {

        List<ScreenDto> testDataList = new ArrayList<>(testDataMap.values());
        this.mockMvc.perform(get("/screens"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(testDataList)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetOne() throws Exception {
        this.mockMvc.perform(get("/screens/1"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(testDataMap.get(1))));

        this.mockMvc.perform(get("/screens/4"))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPost() throws Exception {
        ScreenDto providedDto = new ScreenDto(null, "Screen_110", "Upstairs north wall", "iGlGqaKCPNmd2PmAUlzrRkFPIwpLfxva");
        ScreenDto expectedDto = new ScreenDto(4L, "Screen_110", "Upstairs north wall", "iGlGqaKCPNmd2PmAUlzrRkFPIwpLfxva");

        this.mockMvc.perform(get("/screens/4"))
                .andExpect(status()
                        .isNotFound());

        this.mockMvc.perform(post("/screens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(providedDto)))
                .andExpect(status()
                        .isCreated());

        this.mockMvc.perform(get("/screens/4"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(expectedDto)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPut() throws Exception {
        ScreenDto providedDto = new ScreenDto(null, "Screen_110", "Upstairs north wall", "iGlGqaKCPNmd2PmAUlzrRkFPIwpLfxva");
        ScreenDto expectedDto = new ScreenDto(1L, "Screen_110", "Upstairs north wall", "iGlGqaKCPNmd2PmAUlzrRkFPIwpLfxva");

        this.mockMvc.perform(put("/screens/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(providedDto)))
                .andExpect(status()
                        .isOk());

        this.mockMvc.perform(get("/screens/1"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json(om.writeValueAsString(expectedDto)));

        this.mockMvc.perform(put("/screens/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(providedDto)))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
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
