package com.nhlstenden.student.vigmo.it.controllers;

import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.it.IntegrationTestConfig;
import com.nhlstenden.student.vigmo.it.controllers.logic.AbstractControllerIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringJUnitWebConfig(IntegrationTestConfig.class)
public class RssSlideControllerTest extends AbstractControllerIntegrationTest<RssSlideDto> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public RssSlideControllerTest() {
        super("/rss_slides", 2, 9999);
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
                        jsonPath("$.url").exists(),
                        jsonPath("$.titleTag").exists(),
                        jsonPath("$.descriptionTag").exists(),
                        jsonPath("$.authorTag").exists(),
                        jsonPath("$.categoryTag").exists(),
                        jsonPath("$.imageTag").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetNotFound() throws Exception {
        super.getNotFound().andExpectAll(
                jsonPath("$.error").exists(),
                jsonPath("$.error").value(Matchers.containsString("RssSlideService could not find"))
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
                        jsonPath("$.[:1].url").exists(),
                        jsonPath("$.[:1].titleTag").exists(),
                        jsonPath("$.[:1].descriptionTag").exists(),
                        jsonPath("$.[:1].authorTag").exists(),
                        jsonPath("$.[:1].categoryTag").exists(),
                        jsonPath("$.[:1].imageTag").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        RssSlideDto dto = new RssSlideDto( null,"https://www.nu.nl/rss/Algemeen","title","description","creator","category","enclosure",true,30,"2021-12-21",null,"12:00",null,1L);
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        RssSlideDto dto = new RssSlideDto( 2L,"https://www.nu.nl/rss/Algemeen","title","description","creator","category","enclosure",true,30,"2021-12-21",null,"12:00",null,1L);
        super.postWithExistingId(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        RssSlideDto dto = new RssSlideDto( 2L,"https://www.nu.nl/rss/Algemeen","title","description","creator","category","enclosure",true,30,"2021-12-21",null,"12:00",null,1L);
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        RssSlideDto dto = new RssSlideDto( 2L,"https://www.nu.nl/rss/Algemeen","title","description","creator","category","enclosure",true,30,"2021-12-21",null,"12:00",null,1L);
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
        RssSlideDto dto = new RssSlideDto();
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.slideshowId").exists(),
                jsonPath("$.isActive").exists(),
                jsonPath("$.duration").exists(),
                jsonPath("$.startDate").doesNotExist(),
                jsonPath("$.endDate").doesNotExist(),
                jsonPath("$.startTime").doesNotExist(),
                jsonPath("$.endTime").doesNotExist(),
                jsonPath("$.url").exists(),
                jsonPath("$.titleTag").doesNotExist(),
                jsonPath("$.descriptionTag").doesNotExist(),
                jsonPath("$.authorTag").doesNotExist(),
                jsonPath("$.categoryTag").doesNotExist(),
                jsonPath("$.imageTag").doesNotExist()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        RssSlideDto dto = new RssSlideDto();
        super.modelValidationOnPut(dto).andExpectAll(
                jsonPath("$.slideshowId").exists(),
                jsonPath("$.isActive").exists(),
                jsonPath("$.duration").exists(),
                jsonPath("$.startDate").doesNotExist(),
                jsonPath("$.endDate").doesNotExist(),
                jsonPath("$.startTime").doesNotExist(),
                jsonPath("$.endTime").doesNotExist(),
                jsonPath("$.url").exists(),
                jsonPath("$.titleTag").doesNotExist(),
                jsonPath("$.descriptionTag").doesNotExist(),
                jsonPath("$.authorTag").doesNotExist(),
                jsonPath("$.categoryTag").doesNotExist(),
                jsonPath("$.imageTag").doesNotExist()
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
    public void testFileRenderContentNameAndType() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.get("/rss_slides/2/latest"))
                .andExpect(status()
                        .isOk());
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testInvalidMediaType() throws Exception {
        RssSlideDto dto = new RssSlideDto();
        super.postWithWrongMediaType(dto);
    }
}
