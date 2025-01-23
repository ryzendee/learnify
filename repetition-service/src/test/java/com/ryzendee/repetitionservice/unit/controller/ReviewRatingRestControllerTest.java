package com.ryzendee.repetitionservice.unit.controller;

import com.ryzendee.repetitionservice.contoller.ReviewRatingRestController;
import com.ryzendee.repetitionservice.dto.reviewrating.response.ReviewRatingGetResponse;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@ActiveProfiles("test")
@WebMvcTest(ReviewRatingRestController.class)
public class ReviewRatingRestControllerTest {

    private static final String BASE_URI = "/api/v1/review-ratings";

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification restAssuredRequest;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        restAssuredRequest = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .auth()
                .with(jwt());
    }

    @DisplayName("Should return all review ratings with status OK")
    @Test
    void getAllReviewRatings_shouldReturnResponseWithStatusOk() {
        var expectedReviewRatingGetResponse = new ReviewRatingGetResponse(List.of(ReviewRating.values()));

        var actualResponse = restAssuredRequest.when()
                .get()
                .then()
                .status(HttpStatus.OK)
                .extract()
                .as(ReviewRatingGetResponse.class);

        assertThat(actualResponse).isEqualTo(expectedReviewRatingGetResponse);
    }
}
