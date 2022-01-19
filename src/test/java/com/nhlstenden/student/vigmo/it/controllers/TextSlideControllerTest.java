package com.nhlstenden.student.vigmo.it.controllers;

import com.nhlstenden.student.vigmo.dto.TextSlideDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class TextSlideControllerTest extends AbstractControllerIntegrationTest<TextSlideDto> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public TextSlideControllerTest() {
        super("/text_slides", 3, 9999);
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
                        jsonPath("$.slideshowId").exists(),
                        jsonPath("$.isActive").exists(),
                        jsonPath("$.duration").exists(),
                        jsonPath("$.startDate").exists(),
                        jsonPath("$.endDate").exists(),
                        jsonPath("$.startTime").exists(),
                        jsonPath("$.endTime").exists(),
                        jsonPath("$.title").exists(),
                        jsonPath("$.message").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetNotFound() throws Exception {
        super.getNotFound().andExpectAll(
                jsonPath("$.error").exists(),
                jsonPath("$.error").value(Matchers.containsString("TextSlideService could not find"))
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetAll() throws Exception {
        super.getAll()
                .andExpectAll(
                        jsonPath("$.[:1].id").exists(),
                        jsonPath("$.[:1].slideshowId").exists(),
                        jsonPath("$.[:1].isActive").exists(),
                        jsonPath("$.[:1].duration").exists(),
                        jsonPath("$.[:1].startDate").exists(),
                        jsonPath("$.[:1].endDate").exists(),
                        jsonPath("$.[:1].startTime").exists(),
                        jsonPath("$.[:1].endTime").exists(),
                        jsonPath("$.[:1].title").exists(),
                        jsonPath("$.[:1].message").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        TextSlideDto dto = new TextSlideDto(null, "Title", "Message", 1L, true, 30, null, "2021-12-19", null, "12:00");
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        TextSlideDto dto = new TextSlideDto(3L, "Title", "Message", 1L, true, 30, null, "2021-12-19", null, "12:00");
        super.postWithExistingId(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        TextSlideDto dto = new TextSlideDto(3L, "Title", "Message", 1L, true, 30, null, "2021-12-19", null, "12:00");
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        TextSlideDto dto = new TextSlideDto(3L, "Title", "Message", 1L, true, 30, null, "2021-12-19", null, "12:00");
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
        TextSlideDto dto = new TextSlideDto();
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.slideshowId").exists(),
                jsonPath("$.isActive").exists(),
                jsonPath("$.duration").exists(),
                jsonPath("$.startDate").doesNotExist(),
                jsonPath("$.endDate").doesNotExist(),
                jsonPath("$.startTime").doesNotExist(),
                jsonPath("$.endTime").doesNotExist(),
                jsonPath("$.title").exists(),
                jsonPath("$.message").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        TextSlideDto dto = new TextSlideDto();
        super.modelValidationOnPut(dto).andExpectAll(
                jsonPath("$.slideshowId").exists(),
                jsonPath("$.isActive").exists(),
                jsonPath("$.duration").exists(),
                jsonPath("$.startDate").doesNotExist(),
                jsonPath("$.endDate").doesNotExist(),
                jsonPath("$.startTime").doesNotExist(),
                jsonPath("$.endTime").doesNotExist(),
                jsonPath("$.title").exists(),
                jsonPath("$.message").exists()
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
        TextSlideDto dto = new TextSlideDto();
        super.postWithWrongMediaType(dto);
    }
}
