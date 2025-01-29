package com.ryzendee.userpreferenceservice.unit.controller;

import com.ryzendee.userpreferenceservice.controller.UserPreferenceRestController;
import com.ryzendee.userpreferenceservice.document.notification.NotificationChannel;
import com.ryzendee.userpreferenceservice.document.notification.NotificationType;
import com.ryzendee.userpreferenceservice.dto.options.response.NotificationOptionsResponse;
import com.ryzendee.userpreferenceservice.dto.preference.response.UserPreferenceGetResponse;
import com.ryzendee.userpreferenceservice.exception.UserPreferenceNotFoundException;
import com.ryzendee.userpreferenceservice.service.UserPreferenceService;
import com.ryzendee.userpreferenceservice.testutils.builder.notification.NotificationPreferenceUpdateRequestBuilder;
import com.ryzendee.userpreferenceservice.testutils.builder.userpreference.UserPreferenceGetResponseBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@ActiveProfiles("test")
@WebMvcTest(UserPreferenceRestController.class)
public class UserPreferenceRestControllerTest {

    private static final String BASE_URI = "/api/v1/user-preferences";

    @MockBean
    private UserPreferenceService userPreferenceService;

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification restAssuredRequest;
    private UUID userId;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        restAssuredRequest = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .auth()
                .with(jwt());

        userId = UUID.randomUUID();
    }

    @DisplayName("Should return expected response with status OK when retrieving user preference by userId")
    @Test
    void getUserPreferenceByUserId_shouldReturnStatusOkWithResponse() {
        var expectedPreferenceGetResponse = UserPreferenceGetResponseBuilder.builder().build();
        when(userPreferenceService.getUserPreferenceByUserId(userId))
                .thenReturn(expectedPreferenceGetResponse);

        var actualResponse = restAssuredRequest.when()
                .get("/{userId}", userId.toString())
                .then()
                .status(HttpStatus.OK)
                .extract()
                .as(UserPreferenceGetResponse.class);

        verify(userPreferenceService).getUserPreferenceByUserId(userId);
        assertThat(actualResponse).isEqualTo(expectedPreferenceGetResponse);
    }

    @DisplayName("Should return status NOT FOUND when retrieving user preference because service throws UserPreferenceNotFoundException")
    @Test
    void getUserPreferenceByUserId_shouldStatusBadRequest() {
        doThrow(UserPreferenceNotFoundException.class)
                .when(userPreferenceService).getUserPreferenceByUserId(userId);

        restAssuredRequest.when()
                .get("/{userId}", userId.toString())
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(userPreferenceService).getUserPreferenceByUserId(userId);
    }

    @DisplayName("Should return status OK when updating notification preferences by userId with valid request")
    @Test
    void updateNotificationPreferencesByUserId_shouldReturnStatusOk() {
        var updateRequest = NotificationPreferenceUpdateRequestBuilder.builder().build();
        var expectedPreferenceGetResponse = UserPreferenceGetResponseBuilder.builder().build();
        when(userPreferenceService.updateNotificationPreferenceByUserId(userId, updateRequest))
                .thenReturn(expectedPreferenceGetResponse);

        var actualResponse = restAssuredRequest.body(updateRequest)
                .when()
                .patch("/{userId}/notification-preferences", userId.toString())
                .then()
                .status(HttpStatus.OK)
                .extract()
                .as(UserPreferenceGetResponse.class);

        verify(userPreferenceService).updateNotificationPreferenceByUserId(userId, updateRequest);
        assertThat(actualResponse).isEqualTo(expectedPreferenceGetResponse);
    }

    @DisplayName("Should return status NOT FOUND when updating preferences because user preference does not exists")
    @Test
    void updateNotificationPreferencesByUserId_shouldReturnStatusNotFound() {
        var updateRequest = NotificationPreferenceUpdateRequestBuilder.builder().build();
        doThrow(UserPreferenceNotFoundException.class)
                .when(userPreferenceService).updateNotificationPreferenceByUserId(userId, updateRequest);

        restAssuredRequest.body(updateRequest)
                .when()
                .patch("/{userId}/notification-preferences", userId.toString())
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(userPreferenceService).updateNotificationPreferenceByUserId(userId, updateRequest);
    }

    @DisplayName("Should return status BAD REQUEST when updating notification preferences by userId with invalid request")
    @Test
    void updateNotificationPreferencesByUserId_shouldReturnStatusBadRequest() {
        var updateRequest = NotificationPreferenceUpdateRequestBuilder.builder()
                .withNotificationChannels(new HashMap<>())
                .build();

        restAssuredRequest.body(updateRequest)
                .when()
                .patch("/{userId}/notification-preferences", userId.toString())
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(userPreferenceService, never()).updateNotificationPreferenceByUserId(userId, updateRequest);
    }

    @DisplayName("Should return expected response with status OK when retrieving notification options")
    @Test
    void getNotificationOptions_shouldReturnResponseWithStatusOk() {
        var expectedNotificationOptionsResponse = new NotificationOptionsResponse(
                Arrays.asList(NotificationType.values()),
                Arrays.asList(NotificationChannel.values())
        );

        var actualResponse = restAssuredRequest
                .when()
                .get("/notification-options")
                .then()
                .status(HttpStatus.OK)
                .extract()
                .as(NotificationOptionsResponse.class);

        assertThat(actualResponse).isEqualTo(expectedNotificationOptionsResponse);
    }
}
