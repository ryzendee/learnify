package com.ryzendee.moduleservice.unit.controller;

import com.ryzendee.moduleservice.controller.LearningModuleRestController;
import com.ryzendee.moduleservice.dto.learningmodule.response.LearningModuleResponse;
import com.ryzendee.moduleservice.exception.LearningModuleNotFoundException;
import com.ryzendee.moduleservice.service.learningmodule.LearningModuleService;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleCreateRequestBuilder;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleResponseBuilder;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleUpdateRequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
@ActiveProfiles("test")
@WebMvcTest(LearningModuleRestController.class)
public class LearningModuleRestControllerTest {

    private static final String BASE_URI = "/api/v1/learning-modules";

    private static final int MAX_NAME_LENGTH = 255;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;

    @MockBean
    private LearningModuleService learningModuleService;

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification restAssuredRequest;
    private UUID id;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        restAssuredRequest = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                      .auth()
                      .with(jwt());

        id = UUID.randomUUID();
    }

    @DisplayName("Should return status CREATED when valid request is sent to create a learning module")
    @Test
    void createLearningModule_validRequest_shouldReturnLearningModuleResponseWithStatusCreated() {
        var learningModuleCreateRequest = LearningModuleCreateRequestBuilder.builder().build();
        var expectedLearningModuleResponse = LearningModuleResponseBuilder.builder().build();

        when(learningModuleService.createLearningModule(learningModuleCreateRequest))
                .thenReturn(expectedLearningModuleResponse);

        var actualLearningModuleResponse = restAssuredRequest.body(learningModuleCreateRequest)
                .when()
                .post()
                .then()
                .status(HttpStatus.CREATED)
                .extract()
                .as(LearningModuleResponse.class);
        assertThat(actualLearningModuleResponse).isEqualTo(expectedLearningModuleResponse);

        verify(learningModuleService).createLearningModule(learningModuleCreateRequest);
    }

    @DisplayName("Should return status BAD REQUEST when name field is invalid in create request")
    @MethodSource("getInvalidNameCases")
    @ParameterizedTest
    void createLearningModule_invalidNameField_shouldReturnStatusBadRequest(String invalidName) {
        var learningModuleCreateRequest = LearningModuleCreateRequestBuilder.builder()
                .withName(invalidName)
                .build();

        restAssuredRequest.body(learningModuleCreateRequest)
                .when()
                .post()
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(learningModuleService, never()).createLearningModule(learningModuleCreateRequest);
    }

    @DisplayName("Should return status BAD REQUEST when description field is invalid in create request")
    @MethodSource("getInvalidDescriptionCases")
    @ParameterizedTest
    void createLearningModule_invalidDescriptionField_shouldReturnStatusBadRequest(String invalidDescription) {
        var learningModuleCreateRequest = LearningModuleCreateRequestBuilder.builder()
                .withDescription(invalidDescription)
                .build();

        restAssuredRequest.body(learningModuleCreateRequest)
                .when()
                .post()
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(learningModuleService, never()).createLearningModule(learningModuleCreateRequest);
    }

    @DisplayName("Should return status OK when valid request is sent to update a learning module by ID")
    @Test
    void updateLearningModuleById_validRequest_shouldReturnLearnignModuleResponseWithStatusOk() {
        var learningModuleUpdateRequest = LearningModuleUpdateRequestBuilder.builder().build();
        var expectedLearningModuleResponse = LearningModuleResponseBuilder.builder().build();

        when(learningModuleService.updateLearningModuleById(id, learningModuleUpdateRequest))
                .thenReturn(expectedLearningModuleResponse);

        var actualLearningModuleResponse = restAssuredRequest.body(learningModuleUpdateRequest)
                .when()
                .put("/{id}", id.toString())
                .then()
                .status(HttpStatus.OK)
                .extract()
                .as(LearningModuleResponse.class);
        assertThat(actualLearningModuleResponse).isEqualTo(expectedLearningModuleResponse);

        verify(learningModuleService).updateLearningModuleById(id, learningModuleUpdateRequest);
    }

    @DisplayName("Should return status BAD REQUEST when name field is invalid in update request")
    @MethodSource("getInvalidNameCases")
    @ParameterizedTest
    void updateLearningModuleById_invalidNameField_shouldReturnStatusBadRequest(String invalidName) {
        var learningModuleUpdateRequest = LearningModuleUpdateRequestBuilder.builder()
                .withName(invalidName)
                .build();

        restAssuredRequest.body(learningModuleUpdateRequest)
                .when()
                .put("/{id}", id.toString())
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(learningModuleService, never()).updateLearningModuleById(id, learningModuleUpdateRequest);
    }

    @DisplayName("Should return status BAD REQUEST when description field is invalid in update request")
    @MethodSource("getInvalidDescriptionCases")
    @ParameterizedTest
    void updateLearningModuleById_invalidDescriptionField_shouldReturnStatusBadRequest(String invalidDescription) {
        var learningModuleUpdateRequest = LearningModuleUpdateRequestBuilder.builder()
                .withDescription(invalidDescription)
                .build();

        restAssuredRequest.body(learningModuleUpdateRequest)
                .when()
                .put("/{id}", id.toString())
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(learningModuleService, never()).updateLearningModuleById(id, learningModuleUpdateRequest);
    }

    @DisplayName("Should return status NOT FOUND when LearningModuleNotFoundException is thrown during update")
    @Test
    void updateLearningModuleById_serviceThrowsLearningModuleNotFoundEx_shouldReturnStatusNotFound() {
        var learningModuleUpdateRequest = LearningModuleUpdateRequestBuilder.builder().build();

        doThrow(LearningModuleNotFoundException.class)
                .when(learningModuleService).updateLearningModuleById(id, learningModuleUpdateRequest);

        restAssuredRequest.body(learningModuleUpdateRequest)
                .when()
                .put("/{id}", id.toString())
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(learningModuleService).updateLearningModuleById(id, learningModuleUpdateRequest);
    }

    @DisplayName("Should return status OK when learning module exists by ID")
    @Test
    void getLearningModuleById_existsModule_shouldReturnLearningModuleResponseStatusOk() {
        var expectedLearningModuleResponse = LearningModuleResponseBuilder.builder().build();

        when(learningModuleService.getLearningModuleById(id))
                .thenReturn(expectedLearningModuleResponse);

        var actualLearningModuleResponse = restAssuredRequest.when()
                .get("/{id}", id.toString())
                .then()
                .status(HttpStatus.OK)
                .extract()
                .as(LearningModuleResponse.class);
        assertThat(actualLearningModuleResponse).isEqualTo(expectedLearningModuleResponse);

        verify(learningModuleService).getLearningModuleById(id);
    }

    @DisplayName("Should return status NOT FOUND when LearningModuleNotFoundException is thrown during get")
    @Test
    void getLearningModuleById_serviceThrowsLearningModuleNotFoundEx_shouldReturnStatusNotFound() {
        doThrow(LearningModuleNotFoundException.class)
                .when(learningModuleService).getLearningModuleById(id);

        restAssuredRequest.when()
                .get("/{id}", id.toString())
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(learningModuleService).getLearningModuleById(id);
    }

    @DisplayName("Should return status NO CONTENT when learning module is deleted successfully")
    @Test
    void deleteLearningModuleById_existsModule_shouldReturnStatusNoContent() {
        restAssuredRequest.when()
                .delete("/{id}", id.toString())
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(learningModuleService).deleteLearningModuleById(id);
    }

    @DisplayName("Should return status NOT FOUND when LearningModuleNotFoundException is thrown during delete")
    @Test
    void deleteLearningModuleById_serviceThrowsLearningModuleNotFoundEx_shouldReturnStatusNotFound() {
        doThrow(LearningModuleNotFoundException.class)
                .when(learningModuleService).deleteLearningModuleById(id);

        restAssuredRequest.when()
                .delete("/{id}", id.toString())
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(learningModuleService).deleteLearningModuleById(id);
    }

    @DisplayName("Should return status OK when valid request is sent to get a page of learning modules")
    @Test
    void getLearningModulePage_validRequestParams_shouldReturnPageWithStatusOk() {
        var expectedLearningModuleResponsePage =
                new PageImpl<>(List.of(LearningModuleResponseBuilder.builder().build()));
        var pageable = PageRequest.of(0, 10);

        when(learningModuleService.getLearningPage(pageable))
                .thenReturn(expectedLearningModuleResponsePage);

        restAssuredRequest
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get()
                .then()
                .status(HttpStatus.OK);

        verify(learningModuleService).getLearningPage(pageable);
    }

    private static Stream<Arguments> getInvalidNameCases() {
        return Stream.of(
                arguments(named("Name is null", null)),
                arguments(named("Name is blank", "  ")),
                arguments(named("Name is too long", generateStringWithLength(MAX_NAME_LENGTH + 1)))
        );
    }

    private static Stream<Arguments> getInvalidDescriptionCases() {
        return Stream.of(
                arguments(named("Description is too long", generateStringWithLength(MAX_DESCRIPTION_LENGTH + 1)))
        );
    }

    private static String generateStringWithLength(int length) {
        return RandomStringUtils.random(length);
    }

}
