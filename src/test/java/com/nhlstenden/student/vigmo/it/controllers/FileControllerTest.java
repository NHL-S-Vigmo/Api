package com.nhlstenden.student.vigmo.it.controllers;

import com.nhlstenden.student.vigmo.dto.FileDto;
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
class FileControllerTest extends AbstractControllerIntegrationTest<FileDto> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String USER_ROLE = "ROLE_ADMIN";

    public FileControllerTest() {
        super("/files", 1, 9999);
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
                        jsonPath("$.name").exists(),
                        jsonPath("$.mimeType").exists(),
                        jsonPath("$.data").exists(),
                        jsonPath("$.key").exists()
                );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetNotFound() throws Exception {
        super.getNotFound().andExpectAll(
                jsonPath("$.error").exists(),
                jsonPath("$.error").value(Matchers.containsString("FileService could not find"))
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testGetAll() throws Exception {
        super.getAll().andExpectAll(
                jsonPath("$.[:1].id").exists(),
                jsonPath("$.[:1].name").exists(),
                jsonPath("$.[:1].mimeType").exists(),
                jsonPath("$.[:1].data").exists(),
                jsonPath("$.[:1].key").exists()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPost() throws Exception {
        FileDto dto = new FileDto(null, "Afbeelding1.png", "image/png", "MTIzNDU2Nzg5MDEyMzQ1Njc4OTA=", "P3MNAthDkRhJZq36gx9bp9kw5oqradw63vseAJQKPAtExnm2RjwSco5SZPiXYhBM");
        super.post(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPostWithExistingId() throws Exception {
        FileDto dto = new FileDto(1L, "Afbeelding1.png", "image/png", "MTIzNDU2Nzg5MDEyMzQ1Njc4OTA=", "P3MNAthDkRhJZq36gx9bp9kw5oqradw63vseAJQKPAtExnm2RjwSco5SZPiXYhBM");
        super.postWithExistingId(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPut() throws Exception {
        FileDto dto = new FileDto(1L, "Afbeelding1.png", "image/png", "MTIzNDU2Nzg5MDEyMzQ1Njc4OTA=", "P3MNAthDkRhJZq36gx9bp9kw5oqradw63vseAJQKPAtExnm2RjwSco5SZPiXYhBM");
        super.put(dto);
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testPutNotFound() throws Exception {
        FileDto dto = new FileDto(1L, "Afbeelding1.png", "image/png", "MTIzNDU2Nzg5MDEyMzQ1Njc4OTA=", "P3MNAthDkRhJZq36gx9bp9kw5oqradw63vseAJQKPAtExnm2RjwSco5SZPiXYhBM");
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
        FileDto dto = new FileDto();
        super.modelValidationOnPost(dto).andExpectAll(
                jsonPath("$.name").exists(),
                jsonPath("$.mimeType").exists(),
                jsonPath("$.data").exists(),
                jsonPath("$.key").doesNotExist()
        );
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testModelValidationOnPut() throws Exception {
        FileDto dto = new FileDto();
        super.modelValidationOnPut(dto).andExpectAll(
                jsonPath("$.name").exists(),
                jsonPath("$.mimeType").exists(),
                jsonPath("$.data").exists(),
                jsonPath("$.key").doesNotExist()
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
        getMockMvc().perform(MockMvcRequestBuilders.get("/files/key1/render"))
                .andExpect(status()
                        .isOk())
                .andExpectAll(header().exists("Content-Disposition"),
                        header().string("Content-Disposition",
                                Matchers.containsString("filename")))
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }

    @Test
    @WithMockUser(username = "Jan_Doornbos", authorities = USER_ROLE)
    @Override
    public void testInvalidMediaType() throws Exception {
        FileDto dto = new FileDto();
        super.postWithWrongMediaType(dto);
    }
}