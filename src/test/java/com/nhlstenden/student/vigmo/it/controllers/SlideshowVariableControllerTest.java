package com.nhlstenden.student.vigmo.it.controllers;

import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
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
class SlideshowVariableControllerTest extends AbstractControllerIntegrationTest<SlideshowVariableDto> {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public SlideshowVariableControllerTest() {
        super("/slideshow_variables", 1, 9999);
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
                        jsonPath("$.name").exists(),
                        jsonPath("$.value").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetNotFound() throws Exception {
        super.getNotFound().andExpectAll(
                jsonPath("$.error").exists(),
                jsonPath("$.error").value(Matchers.containsString("SlideshowVariableService could not find"))
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
                        jsonPath("$.[:1].name").exists(),
                        jsonPath("$.[:1].value").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        SlideshowVariableDto dto = new SlideshowVariableDto(null, 1L, "Setting", "setting");
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        SlideshowVariableDto dto = new SlideshowVariableDto(1L, 1L, "Setting", "setting");
        super.postWithExistingId(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        SlideshowVariableDto dto = new SlideshowVariableDto(1L, 1L, "Setting", "setting");
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        SlideshowVariableDto dto = new SlideshowVariableDto(1L, 1L, "Setting", "setting");
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
        SlideshowVariableDto dto = new SlideshowVariableDto();
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.slideshowId").exists(),
                jsonPath("$.name").exists(),
                jsonPath("$.value").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        SlideshowVariableDto dto = new SlideshowVariableDto();
        super.modelValidationOnPut(dto).andExpectAll(
                jsonPath("$.slideshowId").exists(),
                jsonPath("$.name").exists(),
                jsonPath("$.value").exists()
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
        SlideshowVariableDto dto = new SlideshowVariableDto();
        super.postWithWrongMediaType(dto);
    }
}