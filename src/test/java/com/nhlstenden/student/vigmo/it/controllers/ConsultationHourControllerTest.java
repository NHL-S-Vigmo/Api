package com.nhlstenden.student.vigmo.it.controllers;

import com.nhlstenden.student.vigmo.dto.ConsultationHourDto;
import com.nhlstenden.student.vigmo.it.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.it.controllers.logic.AbstractControllerIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class ConsultationHourControllerTest extends AbstractControllerIntegrationTest<ConsultationHourDto> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public ConsultationHourControllerTest() {
        super("/consultation_hours", 1, 9999);
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
                        jsonPath("$.description").exists(),
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
                jsonPath("$.error").value(Matchers.containsString("ConsultationHourService could not find"))
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetAll() throws Exception {
        super.getAll().andExpectAll(
                jsonPath("$.[:1].id").exists(),
                jsonPath("$.[:1].description").exists(),
                jsonPath("$.[:1].weekDay").exists(),
                jsonPath("$.[:1].startTime").exists(),
                jsonPath("$.[:1].endTime").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        ConsultationHourDto dto = new ConsultationHourDto(null,"Noon consultation","MONDAY","12:15","13:00");
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        ConsultationHourDto dto = new ConsultationHourDto(1L,"Noon consultation","MONDAY","12:15","13:00");
        super.postWithExistingId(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        ConsultationHourDto dto = new ConsultationHourDto(1L,"Noon consultation","MONDAY","12:15","13:00");
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        ConsultationHourDto dto = new ConsultationHourDto(1L,"Noon consultation","MONDAY","12:15","13:00");
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
        ConsultationHourDto dto = new ConsultationHourDto();
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
        ConsultationHourDto dto = new ConsultationHourDto();
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

    @WithMockUser(username = "Jan_Doornbos", authorities = "UNKNOWN_ROLE")
    @Test
    @Override
    public void testForbidden() throws Exception {
        super.forbidden();
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testModelValidationWithNoChronologicalTime() throws Exception {
        ConsultationHourDto dto = new ConsultationHourDto(1L,"Noon consultation","MONDAY","12:00","12:00");
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.consultationHourDto").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testInvalidMediaType() throws Exception {
        ConsultationHourDto dto = new ConsultationHourDto();
        super.postWithWrongMediaType(dto);
    }
}
