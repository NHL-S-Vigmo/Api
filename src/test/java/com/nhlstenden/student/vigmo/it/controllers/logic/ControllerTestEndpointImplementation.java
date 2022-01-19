package com.nhlstenden.student.vigmo.it.controllers.logic;

import org.junit.jupiter.api.BeforeEach;

public interface ControllerTestEndpointImplementation {

    /**
     * Should have the {@link BeforeEach} annotation
     * @see BeforeEach
     */
    void beforeEach();

    // -> all default endpoint tests

    void testGetOne() throws Exception;
    void testGetNotFound() throws Exception;
    void testGetAll() throws Exception;
    void testPost() throws Exception;
    void testPostWithExistingId() throws Exception;
    void testPut() throws Exception;
    void testPutNotFound() throws Exception;
    void testDelete() throws Exception;
    void testDeleteNotFound() throws Exception;

    void testModelValidationOnPost() throws Exception;
    void testModelValidationOnPut() throws Exception;

    void testUnauthorized() throws Exception;
    void testForbidden() throws Exception;

    void testInvalidMediaType() throws Exception;
}
