package integration.java.com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.dto.FileDto;
import integration.java.com.nhlstenden.student.vigmo.IntegrationTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
class FileControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private HashMap<Integer, FileDto> testDataMap;
    private ObjectMapper om;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
        testDataMap = new HashMap<>();

        testDataMap.put(1, new FileDto(1L, "Afbeelding1.png", "image/png", "?BLOB?", "P3MNAthDkRhJZq36gx9bp9kw5oqradw63vseAJQKPAtExnm2RjwSco5SZPiXYhBM"));
        testDataMap.put(2, new FileDto(2L, "Afbeelding2.jpeg", "image/jpeg", "?BLOB?", "tWkrpFL5w7xgk440llysWy4QeeIrRQIAyVxoEBqp2eFW4AM9xYta4V2e7Kj27NK5"));
        testDataMap.put(3, new FileDto(3L, "PowerPoint.pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "?BLOB?", "3GuTiufBydD9fcww3289Vf7E23rgRdfaxxy77Cx1LdX1blyVDHcPlEEVALYmRox1"));
    }

    @Test
    public void testGetAll() throws Exception {

        List<FileDto> testDataList = new ArrayList<>(testDataMap.values());

        this.mockMvc.perform(get("/files"))
                .andExpect(status()
                        .isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.name").exists(),
                        jsonPath("$.mimeType").exists(),
                        jsonPath("$.data").exists(),
                        jsonPath("$.key").exists()
                );
    }

}