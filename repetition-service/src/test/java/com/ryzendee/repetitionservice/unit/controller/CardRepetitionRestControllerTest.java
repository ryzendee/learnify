package com.ryzendee.repetitionservice.unit.controller;

import com.ryzendee.repetitionservice.contoller.CardRepetitionRestController;
import com.ryzendee.repetitionservice.dto.repetition.request.CardRepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.response.CardRepetitionGetResponse;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import com.ryzendee.repetitionservice.service.CardRepetitionService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@ActiveProfiles("test")
@WebMvcTest(CardRepetitionRestController.class)
public class CardRepetitionRestControllerTest {

    private static final String BASE_URI = "/api/v1/card-repetitions";

    @MockBean
    private CardRepetitionService cardRepetitionService;

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification restAssuredRequest;
    private UUID learningModuleId;
    private UUID cardId;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        restAssuredRequest = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .auth()
                .with(jwt());

        learningModuleId = UUID.randomUUID();
        cardId = UUID.randomUUID();
    }

    @DisplayName("Should return status OK when retrieving due card repetitions for a learning module")
    @Test
    void getCardRepetitionsForLearningModule_shouldReturnStatusOk() {
        var learningModuleId = UUID.randomUUID();
        var expectedCardRepetitionGetResponseList = List.of(new CardRepetitionGetResponse(cardId));

        when(cardRepetitionService.getCardsForRepetitionByLearningModuleId(learningModuleId))
                .thenReturn(expectedCardRepetitionGetResponseList);

        restAssuredRequest.queryParam("learningModuleId", learningModuleId)
                .when()
                .get("/due")
                .then()
                .status(HttpStatus.OK)
                .extract();

        verify(cardRepetitionService).getCardsForRepetitionByLearningModuleId(learningModuleId);
    }

    @DisplayName("Should return status OK when updating a card repetition by card ID with valid request")
    @Test
    void updateCardRepetitionByCardId_shouldReturnStatusOk() {
        var validRequest = new CardRepetitionUpdateRequest(ReviewRating.EASY);

        restAssuredRequest.body(validRequest)
                .when()
                .put("/{cardId}/review", cardId.toString())
                .then()
                .status(HttpStatus.OK);

        verify(cardRepetitionService).updateCardRepetitionByCardId(cardId, validRequest);
    }

    @DisplayName("Should return status BAD REQUEST when reviewRating is null in update request")
    @NullSource
    @ParameterizedTest
    void updateCardRepetitionByCardId_shouldReturnStatusBadRequest(ReviewRating reviewRating) {
        var invalidRequest = new CardRepetitionUpdateRequest(reviewRating);

        restAssuredRequest.body(invalidRequest)
                .when()
                .put("/{cardId}/review", cardId.toString())
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(cardRepetitionService, never()).updateCardRepetitionByCardId(cardId, invalidRequest);
    }

    @DisplayName("Should return status BAD REQUEST when reviewRating has an invalid format in update request")
    @Test
    void updateCardRepetitionByCardId_shouldReturnStatusBadRequest() {
        restAssuredRequest
                .body(
                        """
                        reviewRating: "invalid"
                        """)
                .when()
                .put("/{cardId}/review", cardId.toString())
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(cardRepetitionService, never())
                .updateCardRepetitionByCardId(eq(cardId), any(CardRepetitionUpdateRequest.class));
    }

}
