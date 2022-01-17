package integration.java.com.nhlstenden.student.vigmo.controllers.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public abstract class AbstractControllerIntegrationTest<DTO> implements ControllerTestEndpointImplementation {
    private final String path;
    private final ObjectMapper om;
    private final long operationId;
    private final long notFoundId;
    private MockMvc mockMvc;

    /**
     * @param path        the path of the controller to test, must start with '/'
     * @param operationId the id to run all the operations on, can be 1
     * @param notFoundId  the id to run all not found operations on, can be 9999
     */
    public AbstractControllerIntegrationTest(String path, long operationId, long notFoundId) {
        this.path = path;
        this.operationId = operationId;
        this.notFoundId = notFoundId;
        this.om = new ObjectMapper();
    }

    public MockMvc getMockMvc(){
        return this.mockMvc;
    }

    public ObjectMapper getObjectMapper(){
        return this.om;
    }

    protected void beforeEach(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    public ResultActions getOne() throws Exception {
        //try to get a slideshow with id 1
        return this.mockMvc.perform(get(path + "/" + operationId))
                .andExpect(status()
                        .isOk());
    }

    public ResultActions getNotFound() throws Exception {
        return this.mockMvc.perform(get(path + "/" + notFoundId))
                .andExpect(status()
                        .isNotFound());
    }

    public ResultActions getAll() throws Exception {
        return this.mockMvc.perform(get(path))
                .andExpect(status()
                        .isOk());
    }

    public ResultActions post(DTO dto) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status()
                        .isCreated())
                .andExpectAll(header()
                        .exists("Location"),
                        header().string("Location", //check if the location header contains a part of the current path.
                                Matchers.containsString(path)));
    }

    public ResultActions postWithExistingId(DTO dto) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status()
                        .isBadRequest());
    }

    public ResultActions put(DTO dto) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.put(path + "/" + operationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status()
                        .isOk());
    }

    public ResultActions putNotFound(DTO dto) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.put(path + "/" + notFoundId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status()
                        .isOk());
    }

    public ResultActions delete() throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.delete(path + "/" + operationId))
                .andExpect(status()
                        .isNoContent());
    }

    public ResultActions deleteNotFound() throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.delete(path + "/" + notFoundId))
                .andExpect(status()
                        .isNotFound());
    }

    public ResultActions modelValidationOnPost(DTO invalidModel) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(invalidModel)))
                .andExpect(status()
                        .isBadRequest());
    }

    public ResultActions modelValidationOnPut(DTO invalidModel) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.put(path + "/" + operationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(invalidModel)))
                .andExpect(status()
                        .isBadRequest());
    }

    // test without mocked user
    public ResultActions unauthorized() throws Exception {
        return this.mockMvc.perform(get(path + "/" + operationId))
                .andExpect(status()
                        .isUnauthorized());
    }

    public ResultActions forbidden() throws Exception {
        return this.mockMvc.perform(get(path + "/" + operationId))
                .andExpect(status()
                        .isForbidden());
    }
}
