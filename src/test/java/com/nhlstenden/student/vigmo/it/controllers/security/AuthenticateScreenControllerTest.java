package com.nhlstenden.student.vigmo.it.controllers.security;

import com.nhlstenden.student.vigmo.it.IntegrationTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
class AuthenticateScreenControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final String path = "/authenticate_screen";
    private final String jwtTokenHeader = "jwt-token";

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void correctScreenLogin() throws Exception {
        this.mockMvc.perform(get(path + "/DMIrM5V5A8dt7QwJ9jk9Q9By4s1351jI"))
                .andExpect(status().
                        isOk()).
                andExpect(header().
                        exists(jwtTokenHeader));
    }

    @Test
    void screenLoginWithNonExistentAuthToken() throws Exception {
        //Given auth token does not belong to any screens
        this.mockMvc.perform(get(path + "/abcdefghijklmnopq"))
                .andExpect(status().
                        isUnauthorized()).
                andExpect(header().
                        doesNotExist(jwtTokenHeader));
    }


    @Test
    void loginWithUsernameAndPasswordAndRequestAResource() throws Exception {
        //get a correct JWT from the signin key of a screen.
        String jwt = this.mockMvc.perform(get(path + "/DMIrM5V5A8dt7QwJ9jk9Q9By4s1351jI"))
                .andExpect(status().
                        isOk()).
                andExpect(header().
                        exists(jwtTokenHeader))
                .andReturn()
                .getResponse()
                .getHeader(jwtTokenHeader);

        //use the jwt response from above to make a request below.
        this.mockMvc.perform(get("/slideshows")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status()
                        .isOk());
    }
}