package com.ryzendee.moduleservice.unit.controller;

import com.ryzendee.moduleservice.controller.CardRestController;
import com.ryzendee.moduleservice.dto.card.response.CardResponse;
import com.ryzendee.moduleservice.exception.CardNotFoundException;
import com.ryzendee.moduleservice.service.card.CardService;
import com.ryzendee.moduleservice.testutils.builder.card.CardCreateRequestBuilder;
import com.ryzendee.moduleservice.testutils.builder.card.CardResponseBuilder;
import com.ryzendee.moduleservice.testutils.builder.card.CardUpdateRequestBuilder;
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
@WebMvcTest(CardRestController.class)
public class CardRestControllerTest {

    private static final String BASE_URI = "/api/v1/learning-modules/{learningModuleId}/cards";

    private static final int MAX_NAME_LENGTH = 255;
    private static final int MAX_DEFINITION_LENGTH = 1000;

    @MockBean
    private CardService cardService;

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification restAssuredRequest;
    private UUID learningModuleId;
    private UUID cardId;


    @BeforeEach
    void setUp() {
        cardId = UUID.randomUUID();
        learningModuleId = UUID.randomUUID();

        RestAssuredMockMvc.basePath = BASE_URI.replace("{learningModuleId}", learningModuleId.toString());
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        restAssuredRequest = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                      .auth()
                      .with(jwt());

    }

    @DisplayName("Should return status CREATED when valid request is sent to create a card")
    @Test
    void createCard_validRequest_shouldReturnCardResponseWithStatusCreated() {
        var cardCreateRequest = CardCreateRequestBuilder.builder().build();
        var expectedCardResponse = CardResponseBuilder.builder().build();

        when(cardService.createCard(learningModuleId, cardCreateRequest))
                .thenReturn(expectedCardResponse);

        var actualCardResponse = restAssuredRequest.body(cardCreateRequest)
                .when()
                .post()
                .then()
                .status(HttpStatus.CREATED)
                .extract()
                .as(CardResponse.class);
        assertThat(actualCardResponse).isEqualTo(expectedCardResponse);

        verify(cardService).createCard(learningModuleId, cardCreateRequest);
    }

    @DisplayName("Should return status BAD REQUEST when question field is invalid in create request")
    @MethodSource("getInvalidQuestionCases")
    @ParameterizedTest
    void createCard_invalidQuestionField_shouldReturnStatusBadRequest(String invalidQuestion) {
        var cardCreateRequest = CardCreateRequestBuilder.builder()
                .withQuestion(invalidQuestion)
                .build();

        restAssuredRequest.body(cardCreateRequest)
                .when()
                .post()
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(cardService, never()).createCard(learningModuleId, cardCreateRequest);
    }

    @DisplayName("Should return status BAD REQUEST when answer field is invalid in create request")
    @MethodSource("getInvalidAnswerCases")
    @ParameterizedTest
    void createCard_invalidAnswerField_shouldReturnStatusBadRequest(String invalidAnswer) {
        var cardCreateRequest = CardCreateRequestBuilder.builder()
                .withAnswer(invalidAnswer)
                .build();

        restAssuredRequest.body(cardCreateRequest)
                .when()
                .post()
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(cardService, never()).createCard(learningModuleId, cardCreateRequest);
    }

    @DisplayName("Should return status OK when valid request is sent to update a card by ID")
    @Test
    void updateCardById_validRequest_shouldReturnCardResponseWithStatusOk() {
        var cardUpdateRequest = CardUpdateRequestBuilder.builder().build();
        var expectedCardResponse = CardResponseBuilder.builder().build();

        when(cardService.updateCardById(cardId, cardUpdateRequest))
                .thenReturn(expectedCardResponse);

        var actualCardResponse = restAssuredRequest.body(cardUpdateRequest)
                .when()
                .put("/{id}", cardId.toString())
                .then()
                .status(HttpStatus.OK)
                .extract()
                .as(CardResponse.class);
        assertThat(actualCardResponse).isEqualTo(expectedCardResponse);

        verify(cardService).updateCardById(cardId, cardUpdateRequest);
    }

    @DisplayName("Should return status BAD REQUEST when question field is invalid in update request")
    @MethodSource("getInvalidQuestionCases")
    @ParameterizedTest
    void updateCardById_invalidQuestionField_shouldReturnStatusBadRequest(String invalidQuestion) {
        var cardUpdateRequest = CardUpdateRequestBuilder.builder()
                .withAnswer(invalidQuestion)
                .build();

        restAssuredRequest.body(cardUpdateRequest)
                .when()
                .put("/{id}", cardId.toString())
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(cardService, never()).updateCardById(cardId, cardUpdateRequest);
    }

    @DisplayName("Should return status BAD REQUEST when answer field is invalid in update request")
    @MethodSource("getInvalidAnswerCases")
    @ParameterizedTest
    void updateCardById_invalidAnswerField_shouldReturnStatusBadRequest(String invalidAnswer) {
        var cardUpdateRequest = CardUpdateRequestBuilder.builder()
                .withQuestion(invalidAnswer)
                .build();

        restAssuredRequest.body(cardUpdateRequest)
                .when()
                .put("/{id}", cardId.toString())
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(cardService, never()).updateCardById(cardId, cardUpdateRequest);
    }

    @DisplayName("Should return status NOT FOUND when CardNotFoundException is thrown during update")
    @Test
    void updateCardById_serviceThrowsCardNotFoundEx_shouldReturnStatusNotFound() {
        var cardUpdateRequest = CardUpdateRequestBuilder.builder().build();

        doThrow(CardNotFoundException.class)
                .when(cardService).updateCardById(cardId, cardUpdateRequest);

        restAssuredRequest.body(cardUpdateRequest)
                .when()
                .put("/{id}", cardId.toString())
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(cardService).updateCardById(cardId, cardUpdateRequest);
    }

    @DisplayName("Should return status OK when card exists by ID")
    @Test
    void getCardById_existsCard_shouldReturnCardResponseStatusOk() {
        var expectedCardResponse = CardResponseBuilder.builder().build();

        when(cardService.getCardById(cardId))
                .thenReturn(expectedCardResponse);

        var actualCardResponse = restAssuredRequest.when()
                .get("/{id}", cardId.toString())
                .then()
                .status(HttpStatus.OK)
                .extract()
                .as(CardResponse.class);
        assertThat(actualCardResponse).isEqualTo(expectedCardResponse);

        verify(cardService).getCardById(cardId);
    }

    @DisplayName("Should return status NOT FOUND when CardNotFoundException is thrown during get")
    @Test
    void getCardById_serviceThrowsCardNotFoundEx_shouldReturnStatusNotFound() {
        doThrow(CardNotFoundException.class)
                .when(cardService).getCardById(cardId);

        restAssuredRequest.when()
                .get("/{id}", cardId.toString())
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(cardService).getCardById(cardId);
    }

    @DisplayName("Should return status NO CONTENT when card is deleted successfully")
    @Test
    void deleteCardById_existsCard_shouldReturnStatusNoContent() {
        restAssuredRequest.when()
                .delete("/{id}", cardId.toString())
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(cardService).deleteCardById(cardId);
    }

    @DisplayName("Should return status NOT FOUND when CardNotFoundException is thrown during delete")
    @Test
    void deleteCardById_serviceThrowsCardNotFoundEx_shouldReturnStatusNotFound() {
        doThrow(CardNotFoundException.class)
                .when(cardService).deleteCardById(cardId);

        restAssuredRequest.when()
                .delete("/{id}", cardId.toString())
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(cardService).deleteCardById(cardId);
    }

    @DisplayName("Should return status OK when valid request is sent to get a page of cards")
    @Test
    void getCardPage_validRequestParams_shouldReturnPageWithStatusOk() {
        var expectedCardResponsePage =
                new PageImpl<>(List.of(CardResponseBuilder.builder().build()));
        var pageable = PageRequest.of(0, 10);

        when(cardService.getCardPageByLearningModuleId(learningModuleId, pageable))
                .thenReturn(expectedCardResponsePage);

        restAssuredRequest
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get()
                .then()
                .status(HttpStatus.OK);

        verify(cardService).getCardPageByLearningModuleId(learningModuleId, pageable);
    }

    private static Stream<Arguments> getInvalidQuestionCases() {
        return Stream.of(
                arguments(named("Question is null", null)),
                arguments(named("Question is blank", "  ")),
                arguments(named("Question is too long", generateStringWithLength(MAX_NAME_LENGTH + 1)))
        );
    }

    private static Stream<Arguments> getInvalidAnswerCases() {
        return Stream.of(
                arguments(named("Answer is null", null)),
                arguments(named("Answer is blank", "  ")),
                arguments(named("Answer is too long", generateStringWithLength(MAX_DEFINITION_LENGTH + 1)))
        );
    }

    private static String generateStringWithLength(int length) {
        return RandomStringUtils.random(length);
    }
}
