package com.nhlstenden.student.vigmo.it.controllers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlstenden.student.vigmo.security.models.LoginDto;
import com.nhlstenden.student.vigmo.it.IntegrationTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
class AuthenticateControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper om;
    private final String path = "/authenticate";
    private final String jwtTokenHeader = "jwt-token";

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        om = new ObjectMapper();
    }

    @Test
    void correctLogin() throws Exception {
        LoginDto loginDto = new LoginDto("Thijs_Smegen", "password123");
        this.mockMvc.perform(post(path).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(loginDto)))
                .andExpect(status().
                        isOk()).
                andExpect(header().
                        exists(jwtTokenHeader));
    }

    @Test
    void invalidPasswordLogin() throws Exception {
        LoginDto loginDto = new LoginDto("Thijs_Smegen", "123456");
        this.mockMvc.perform(post(path).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(loginDto)))
                .andExpect(status().
                        isUnauthorized()).
                andExpect(header().
                        doesNotExist(jwtTokenHeader));
    }

    @Test
    void nonExistentUserLogin() throws Exception {
        LoginDto loginDto = new LoginDto("NonExistentUser", "password123");
        this.mockMvc.perform(post(path).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(loginDto)))
                .andExpect(status().
                        isUnauthorized()).
                andExpect(header().
                        doesNotExist(jwtTokenHeader));
    }

    @Test
    void disabledUserLogin() throws Exception {
        LoginDto loginDto = new LoginDto("Jan_Doornbos", "password321");
        this.mockMvc.perform(post(path).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(loginDto)))
                .andExpect(status().
                        isForbidden()).
                andExpect(header().
                        doesNotExist(jwtTokenHeader));
    }

    @Test
    void disabledUserLoginWithWrongPassword() throws Exception {
        LoginDto loginDto = new LoginDto("Jan_Doornbos", "123456");
        this.mockMvc.perform(post(path).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(loginDto)))
                .andExpect(status()
                        .isForbidden())
                .andExpect(header()
                        .doesNotExist(jwtTokenHeader));
    }

    @Test
    void loginWithWrongMediaType() throws Exception {
        LoginDto loginDto = new LoginDto("Thijs_Smegen", "password123");
        this.mockMvc.perform(post(path).
                        contentType(MediaType.TEXT_PLAIN)
                        .content(om.writeValueAsString(loginDto)))
                .andExpect(status()
                        .isUnsupportedMediaType())
                .andExpect(header()
                        .doesNotExist(jwtTokenHeader));
    }

    @Test
    void loginWithUsernameAndPasswordAndRequestAResource() throws Exception {
        LoginDto loginDto = new LoginDto("Thijs_Smegen", "password123");

        //sign in with the above model
        String jwt = loginAndGetDto(loginDto);

        //use the jwt response from above to make a request below.
        this.mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status()
                        .isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * Sign in with the provided DTO and get the jwt that belongs to it.
     * @param loginDto model with username and password
     * @return returns the JWT token generated by the request.
     * @throws Exception generates exception if credentials are invalid or if the mockMVC went wrong.
     */
    private String loginAndGetDto(LoginDto loginDto) throws Exception {
        return this.mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(loginDto)))
                .andExpect(status().
                        isOk()).
                andExpect(header().
                        exists(jwtTokenHeader))
                .andReturn().getResponse().getHeader(jwtTokenHeader);
    }
}