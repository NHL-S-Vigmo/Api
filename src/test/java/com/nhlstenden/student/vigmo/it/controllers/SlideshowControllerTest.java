package com.nhlstenden.student.vigmo.it.controllers;

import com.nhlstenden.student.vigmo.dto.SlideshowDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class SlideshowControllerTest extends AbstractControllerIntegrationTest<SlideshowDto> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public SlideshowControllerTest() {
        super("/slideshows", 1, 9999);
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
                        jsonPath("$.screenId").exists(),
                        jsonPath("$.name").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetNotFound() throws Exception {
        super.getNotFound().andExpectAll(
                jsonPath("$.error").exists(),
                jsonPath("$.error").value(Matchers.containsString("SlideshowService could not find"))
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetAll() throws Exception {
        super.getAll()
                .andExpectAll(
                        jsonPath("$.[:1].id").exists(),
                        jsonPath("$.[:1].screenId").exists(),
                        jsonPath("$.[:1].name").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        SlideshowDto dto = new SlideshowDto(null,3L,"Christmas");
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        SlideshowDto dto = new SlideshowDto(1L,3L,"Christmas");
        super.postWithExistingId(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        SlideshowDto dto = new SlideshowDto(1L,3L,"Christmas");
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        SlideshowDto dto = new SlideshowDto(1L,3L,"Christmas");
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
        SlideshowDto dto = new SlideshowDto();
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.screenId").doesNotExist(),
                jsonPath("$.name").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        SlideshowDto dto = new SlideshowDto();
        super.modelValidationOnPut(dto).andExpectAll(
                jsonPath("$.screenId").doesNotExist(),
                jsonPath("$.name").exists()
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
        SlideshowDto dto = new SlideshowDto();
        super.postWithWrongMediaType(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    public void testGetVariables() throws Exception {
        getMockMvc().perform(get(getPath() + "/1/variables"))
                .andExpect(status()
                        .isOk()).andExpectAll(
                jsonPath("$.[:1].id").exists(),
                jsonPath("$.[:1].slideshowId").exists(),
                jsonPath("$.[:1].name").exists(),
                jsonPath("$.[:1].value").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    public void testGetVariablesFromNonExistentSlideshow() throws Exception {
        getMockMvc().perform(get(getPath() + "/999/variables"))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    public void testGetSlides() throws Exception {
        getMockMvc().perform(get(getPath() + "/1/slides"))
                .andExpect(status()
                        .isOk()).andExpectAll(
                        jsonPath("$.[:1].slideId").exists(),
                        jsonPath("$.[:1].isActive").exists(),
                        jsonPath("$.[:1].duration").exists(),
                        jsonPath("$.[:1].path").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    public void testGetSlidesFromNonExistentSlideshow() throws Exception {
        getMockMvc().perform(get(getPath() + "/999/slides"))
                .andExpect(status()
                        .isNotFound()
                );
    }

    @Test
    public void testGetSlideshowsBelongingToScreenOnly() throws Exception {
        //get a correct JWT from the signin key of a screen.
        String jwt = getMockMvc().perform(get("/authenticate_screen/DMIrM5V5A8dt7QwJ9jk9Q9By4s1351jI"))
                .andExpect(status().
                        isOk()).
                andExpect(header().
                        exists("jwt-token"))
                .andReturn()
                .getResponse()
                .getHeader("jwt-token");

        //use the jwt response from above to make a request below.
        getMockMvc().perform(get("/slideshows")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status()
                        .isOk())
                .andExpect(jsonPath("$.length()")
                        .value(1));
    }
}
