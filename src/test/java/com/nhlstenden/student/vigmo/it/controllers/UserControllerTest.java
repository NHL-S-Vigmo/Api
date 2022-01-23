package com.nhlstenden.student.vigmo.it.controllers;

import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.it.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.it.controllers.logic.AbstractControllerIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class UserControllerTest extends AbstractControllerIntegrationTest<UserDto> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public UserControllerTest() {
        super("/users", 1, 9999);
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
                        jsonPath("$.username").exists(),
                        jsonPath("$.password").exists(),
                        jsonPath("$.enabled").exists(),
                        jsonPath("$.role").exists(),
                        jsonPath("$.pfpLocation").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetNotFound() throws Exception {
        super.getNotFound().andExpectAll(
                jsonPath("$.error").exists(),
                jsonPath("$.error").value(Matchers.containsString("UserService could not find"))
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetAll() throws Exception {
        super.getAll()
                .andExpectAll(
                        jsonPath("$.[:1].id").exists(),
                        jsonPath("$.[:1].username").exists(),
                        jsonPath("$.[:1].password").exists(),
                        jsonPath("$.[:1].enabled").exists(),
                        jsonPath("$.[:1].role").exists(),
                        jsonPath("$.[:1].pfpLocation").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        UserDto dto = new UserDto(null, "Thijs_Smegen1", "", true, "ROLE_ADMIN", "/image_013.jpg");
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        UserDto dto = new UserDto(1L, "Thijs_Smegen2", "", true, "ROLE_ADMIN", "/image_013.jpg");
        super.postWithExistingId(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        UserDto dto = new UserDto(1L, "Thijs_Smegen3", "", true, "ROLE_ADMIN", "/image_013.jpg");
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        UserDto dto = new UserDto(1L, "Thijs_Smegen4", "", true, "ROLE_ADMIN", "/image_013.jpg");
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
        UserDto dto = new UserDto();
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.username").exists(),
                jsonPath("$.password").doesNotExist(),
                jsonPath("$.enabled").exists(),
                jsonPath("$.role").exists(),
                jsonPath("$.pfpLocation").doesNotExist()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        UserDto dto = new UserDto();
        super.modelValidationOnPut(dto).andExpectAll(
                jsonPath("$.username").exists(),
                jsonPath("$.password").doesNotExist(),
                jsonPath("$.enabled").exists(),
                jsonPath("$.role").exists(),
                jsonPath("$.pfpLocation").doesNotExist()
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
    public void testCreateExistingUsername() throws Exception {
        UserDto user = new UserDto(null, "Thijs_Smegen", "password", true, "ROLE_DOCENT", "");

        getMockMvc().perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(user)))
                .andExpect(status()
                        .isConflict());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    public void changePasswordUsingPut() throws Exception {
        UserDto user = new UserDto(1L, "Thijs_Smegen", "password1", true, "ROLE_ADMIN", "");

        getMockMvc().perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(user)))
                .andExpect(status()
                        .isOk());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testInvalidMediaType() throws Exception {
        UserDto dto = new UserDto();
        super.postWithWrongMediaType(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPostAsDocentNotAllowed() throws Exception {
        UserDto dto = new UserDto(null, "Thijs_Smegen3", "", true, "ROLE_ADMIN", "/image_013.jpg");
        getMockMvc().perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(dto)))
                .andExpect(status()
                        .isForbidden())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testPutAsDocentNotAllowedOnOtherUser() throws Exception {
        UserDto dto = new UserDto(1L, "Thijs_Smegen3", "", true, "ROLE_ADMIN", "/image_013.jpg");
        getMockMvc().perform(MockMvcRequestBuilders.put("/users/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(dto)))
                .andExpect(status()
                        .isForbidden())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testDeleteAsDocentNotAllowed() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(status()
                        .isForbidden())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void testGetAsDocentOnOtherUserNotAllowed() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status()
                        .isForbidden())
                .andExpect(jsonPath("$.error").exists());
    }
}
