package integration.java.com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.dto.UserDto;
import integration.java.com.nhlstenden.student.vigmo.IntegrationTestConfig;
import integration.java.com.nhlstenden.student.vigmo.controllers.logic.AbstractControllerIntegrationTest;
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
                .andExpect(
                        jsonPath("$.name").exists());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetNotFound() throws Exception {
        super.getNotFound();
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetAll() throws Exception {
        super.getAll();
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        UserDto dto = new UserDto();
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        UserDto dto = new UserDto();
        dto.setId(1L);
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        UserDto dto = new UserDto();
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        UserDto dto = new UserDto();
        super.put(dto);
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
        super.modelValidationOnPost(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        UserDto dto = new UserDto();
        super.modelValidationOnPut(dto);
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
    public void testCreateExistingUsername() throws Exception {
        UserDto user = new UserDto(null, "Thijs_Smegen", "password", true, "ROLE_DOCENT", "");

        getMockMvc().perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(user)))
                .andExpect(status()
                        .isConflict());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = "ROLE_DOCENT")
    public void changePasswordUsingPut() throws Exception {
        UserDto user = new UserDto(1L, "Thijs_Smegen", "password1", true, "ROLE_ADMIN", "");

        getMockMvc().perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(user)))
                .andExpect(status()
                        .isOk());
    }
}
