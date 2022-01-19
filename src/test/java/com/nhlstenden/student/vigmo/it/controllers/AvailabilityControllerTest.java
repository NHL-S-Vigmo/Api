package com.nhlstenden.student.vigmo.it.controllers;

import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.it.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.it.controllers.logic.AbstractControllerIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.hamcrest.Matchers;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class AvailabilityControllerTest extends AbstractControllerIntegrationTest<AvailabilityDto> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public AvailabilityControllerTest() {
        super("/availabilities", 1, 9999);
    }

    @BeforeEach
    @Override
    public void beforeEach() {
        super.beforeEach(webApplicationContext);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetOne() throws Exception {
        super.getOne()
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.userId").exists(),
                        jsonPath("$.weekDay").exists(),
                        jsonPath("$.startTime").exists(),
                        jsonPath("$.endTime").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetNotFound() throws Exception {
        super.getNotFound().andExpectAll(
                jsonPath("$.error").exists(),
                jsonPath("$.error").value(Matchers.containsString("AvailabilityService could not find"))
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetAll() throws Exception {
        super.getAll().andExpectAll(
                jsonPath("$.[:1].id").exists(),
                jsonPath("$.[:1].userId").exists(),
                jsonPath("$.[:1].weekDay").exists(),
                jsonPath("$.[:1].startTime").exists(),
                jsonPath("$.[:1].endTime").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        AvailabilityDto dto = new AvailabilityDto(null, 1L, "MONDAY", "09:15", "16:00");
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        AvailabilityDto dto = new AvailabilityDto(1L, 1L, "MONDAY", "09:15", "16:00");
        super.postWithExistingId(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        AvailabilityDto dto = new AvailabilityDto(1L, 1L, "MONDAY", "09:15", "16:00");
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        AvailabilityDto dto = new AvailabilityDto(1L, 1L, "MONDAY", "09:15", "16:00");
        super.putNotFound(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testDelete() throws Exception {
        super.delete();
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testDeleteNotFound() throws Exception {
        super.deleteNotFound();
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPost() throws Exception {
        AvailabilityDto dto = new AvailabilityDto();
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.description").doesNotExist(),
                jsonPath("$.weekDay").exists(),
                jsonPath("$.startTime").exists(),
                jsonPath("$.endTime").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        AvailabilityDto dto = new AvailabilityDto();
        super.modelValidationOnPut(dto).andExpectAll(
                jsonPath("$.description").doesNotExist(),
                jsonPath("$.weekDay").exists(),
                jsonPath("$.startTime").exists(),
                jsonPath("$.endTime").exists()
        );
    }

    @Test
    @Override
    public void testUnauthorized() throws Exception {
        super.unauthorized();
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "UNKNOWN_ROLE")
    @Override
    public void testForbidden() throws Exception {
        super.forbidden();
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testInvalidMediaType() throws Exception {
        AvailabilityDto dto = new AvailabilityDto();
        super.postWithWrongMediaType(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testCreateWithUnknownUserId() throws Exception {
        AvailabilityDto nonExistentUserDto = new AvailabilityDto(null, 9999L, "MONDAY", "10:00", "11:00");
        //test creating a record with an invalid user id
        getMockMvc().perform(MockMvcRequestBuilders.post("/availabilities").
                        contentType(MediaType.APPLICATION_JSON).
                        content(getObjectMapper().writeValueAsString(nonExistentUserDto))).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testUpdateWithUnknownUserId() throws Exception {
        AvailabilityDto nonExistentUserDto = new AvailabilityDto(null, 9999L, "MONDAY", "10:00", "11:00");

        getMockMvc().perform(MockMvcRequestBuilders.put("/availabilities/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(getObjectMapper().writeValueAsString(nonExistentUserDto))).
                andExpect(status().
                        isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testModelValidationWithNoChronologicalTime() throws Exception {
        AvailabilityDto dto = new AvailabilityDto(1L, 1L, "MONDAY", "10:00", "10:00");
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.availabilityDto").exists()
        );
    }
}
