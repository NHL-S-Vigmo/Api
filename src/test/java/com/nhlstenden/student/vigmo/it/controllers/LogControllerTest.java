package com.nhlstenden.student.vigmo.it.controllers;

import com.nhlstenden.student.vigmo.dto.LogDto;
import com.nhlstenden.student.vigmo.it.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.it.controllers.logic.AbstractControllerIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class LogControllerTest extends AbstractControllerIntegrationTest<LogDto> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public LogControllerTest() {
        super("/logs", 1, 9999);
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
                        jsonPath("$.username").exists(),
                        jsonPath("$.action").exists(),
                        jsonPath("$.message").exists(),
                        jsonPath("$.datetime").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetNotFound() throws Exception {
        super.getNotFound().andExpectAll(
                jsonPath("$.error").exists(),
                jsonPath("$.error").value(Matchers.containsString("LogService could not find"))
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetAll() throws Exception {
        super.getAll().andExpectAll(
                jsonPath("$.[:1].id").exists(),
                jsonPath("$.[:1].userId").exists(),
                jsonPath("$.[:1].username").exists(),
                jsonPath("$.[:1].action").exists(),
                jsonPath("$.[:1].message").exists(),
                jsonPath("$.[:1].datetime").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        LogDto dto = new LogDto(null, 1L, "Jan_Doornbos", "Create log", "created a log", null);
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        LogDto dto = new LogDto(1L, 1L, "Jan_Doornbos", "Create log", "created a log", null);
        dto.setId(1L);
        super.postWithExistingId(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        LogDto dto = new LogDto(1L, 1L, "Jan_Doornbos", "Create log", "created a log", null);
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        LogDto dto = new LogDto(1L, 1L, "Jan_Doornbos", "Create log", "created a log", null);
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
        LogDto dto = new LogDto();
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.user_id").doesNotExist(),
                jsonPath("$.username").exists(),
                jsonPath("$.action").exists(),
                jsonPath("$.message").doesNotExist(),
                jsonPath("$.datetime").doesNotExist()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        LogDto dto = new LogDto();
        super.modelValidationOnPut(dto).andExpectAll(
                jsonPath("$.user_id").doesNotExist(),
                jsonPath("$.username").exists(),
                jsonPath("$.action").exists(),
                jsonPath("$.message").doesNotExist(),
                jsonPath("$.datetime").doesNotExist()
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
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testInvalidMediaType() throws Exception {
        LogDto dto = new LogDto();
        super.postWithWrongMediaType(dto);
    }
}
