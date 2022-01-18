package integration.java.com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import integration.java.com.nhlstenden.student.vigmo.IntegrationTestConfig;
import integration.java.com.nhlstenden.student.vigmo.controllers.logic.AbstractControllerIntegrationTest;
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
public class RssSlideControllerTest extends AbstractControllerIntegrationTest<RssSlideDto> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public RssSlideControllerTest() {
        super("/rss_slides", 1, 9999);
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
        super.getAll();
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        RssSlideDto dto = new RssSlideDto();
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        RssSlideDto dto = new RssSlideDto();
        dto.setId(1L);
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        RssSlideDto dto = new RssSlideDto();
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        RssSlideDto dto = new RssSlideDto();
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
        RssSlideDto dto = new RssSlideDto();
        super.modelValidationOnPost(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        RssSlideDto dto = new RssSlideDto();
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
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testInvalidMediaType() throws Exception {
        RssSlideDto dto = new RssSlideDto();
        super.postWithWrongMediaType(dto);
    }
}
