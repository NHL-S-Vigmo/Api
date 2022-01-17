package integration.java.com.nhlstenden.student.vigmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.dto.ConsultationHourDto;
import com.nhlstenden.student.vigmo.dto.MediaSlideDto;
import integration.java.com.nhlstenden.student.vigmo.IntegrationTestConfig;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class MediaSlideControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private HashMap<Integer, MediaSlideDto> testDataMap;
    private ObjectMapper om;


    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
        testDataMap = new HashMap<>();
        testDataMap.put(1, new MediaSlideDto(1L,true,"video","/videos/2021/2/christmas.mp4",true,60,"2021-12-20","2021-12-20","10:00","11:00",1L));
        testDataMap.put(2, new MediaSlideDto(4L,false,"video","/videos/2021/3/exams.mkv",true,30,null,null,null,null,2L));

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetAll() throws Exception {
        List<MediaSlideDto> testDataList = new ArrayList<>(testDataMap.values());

        this.mockMvc.perform(get("/media_slides")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataList)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetOne() throws  Exception {
        this.mockMvc.perform(get("/media_slides/1")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(testDataMap.get(1))));
        this.mockMvc.perform(get("/media_slides/2")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(get("/media_slides/8")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPost() throws Exception {
        MediaSlideDto providedDto = new MediaSlideDto(null,true,"/videos/2021/2/test.mp4","video",true,80,"2021-12-24","2021-12-26","12:00","12:30",2L);

        MvcResult result = this.mockMvc.perform(post("/media_slides").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isCreated()).andReturn();
        int location = Integer.parseInt(Objects.requireNonNull(result.getResponse()
                        .getHeader("Location"))
                .replace("/media_slides/", ""));
        MediaSlideDto expectedDto =  new MediaSlideDto((long) location,true,"/videos/2021/2/test.mp4","video",true,80,"2021-12-24","2021-12-26","12:00","12:30",2L);

        this.mockMvc.perform(get("/media_slides/" + location)).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPut() throws Exception {
        MediaSlideDto providedDto = new MediaSlideDto(null,true,"/videos/2021/2/test.mp4","video",true,80,"2021-12-24","2021-12-26","12:00","12:30",2L);
        MediaSlideDto expectedDto =  new MediaSlideDto(1L,true,"/videos/2021/2/test.mp4","video",true,80,"2021-12-24","2021-12-26","12:00","12:30",2L);

        this.mockMvc.perform(put("/media_slides/1").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isOk());
        this.mockMvc.perform(get("/media_slides/1")).
                andExpect(status().
                        isOk()).
                andExpect(content().
                        json(om.writeValueAsString(expectedDto)));
        this.mockMvc.perform(put("/media_slides/2").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(put("/media_slides/8").
                contentType(MediaType.APPLICATION_JSON).
                content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isNotFound());

    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testDelete() throws Exception {
        this.mockMvc.perform(delete("/media_slides/1")).
                andExpect(status().
                        isNoContent());
        this.mockMvc.perform(get("/media_slides/1")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(delete("/media_slides/1")).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(delete("/media_slides/2")).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testUserValidation() throws Exception {
        MediaSlideDto nonExistentSlideshowDto = new MediaSlideDto(null,true,"/videos/2021/2/test.mp4","video",true,80,"2021-12-24","2021-12-26","12:00","12:30",4L);
        this.mockMvc.perform(post("/media_slides").
                        contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(nonExistentSlideshowDto))).
                andExpect(status().
                        isNotFound());
        this.mockMvc.perform(put("/media_slides/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(nonExistentSlideshowDto))).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testModelValidation() throws Exception {
        MediaSlideDto providedDto = new MediaSlideDto(null,true,"","",true,0,"2021-12-2a","2021-12-2a","12:0a","12:3a",4L);
        //test if the model is invalid
        this.mockMvc.perform(post("/media_slides")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(om.writeValueAsString(providedDto))).
                andExpect(status().
                        isBadRequest())

                //expect the below validation errors to exist
                .andExpectAll(
                        jsonPath("$.type").exists(),
                        jsonPath("$.resource").exists(),
                        jsonPath("$.duration").exists(),
                        jsonPath("$.startDate").exists(),
                        jsonPath("$.endDate").exists(),
                        jsonPath("$.startTime").exists(),
                        jsonPath("$.endTime").exists()
                );
    }
}
