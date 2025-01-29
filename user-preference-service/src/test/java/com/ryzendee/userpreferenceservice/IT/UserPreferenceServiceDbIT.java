package com.ryzendee.userpreferenceservice.IT;

import com.ryzendee.userpreferenceservice.document.UserPreferenceDocument;
import com.ryzendee.userpreferenceservice.exception.UserPreferenceNotFoundException;
import com.ryzendee.userpreferenceservice.mapper.UserPreferenceGetResponseMapper;
import com.ryzendee.userpreferenceservice.repository.UserPreferenceRepository;
import com.ryzendee.userpreferenceservice.service.UserPreferenceService;
import com.ryzendee.userpreferenceservice.testutils.base.BaseTestcontainersTest;
import com.ryzendee.userpreferenceservice.testutils.builder.notification.NotificationPreferenceUpdateRequestBuilder;
import com.ryzendee.userpreferenceservice.testutils.builder.userpreference.UserPreferenceDocumentBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest
public class UserPreferenceServiceDbIT extends BaseTestcontainersTest {

    @Autowired
    private UserPreferenceService userPreferenceService;
    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @MockBean
    private UserPreferenceGetResponseMapper userPreferenceGetResponseMapper;

    private UserPreferenceDocument preparedDocument;

    @BeforeAll
    static void startupContainer() {
        mongoDBContainer.start();
    }

    @BeforeEach
    void setUp() {
        deleteAllDocuments();
        preparedDocument = insertUserPreferenceDocument();
    }

    @DisplayName("Should return user preference by id when document is exists")
    @Test
    void getUserPreferenceByUserId_shouldReturnGetResponse() {
        var argumentCaptor = ArgumentCaptor.forClass(UserPreferenceDocument.class);

        userPreferenceService.getUserPreferenceByUserId(preparedDocument.getUserId());

        verify(userPreferenceGetResponseMapper).map(argumentCaptor.capture());
        var capturedDocument = argumentCaptor.getValue();
        assertThat(capturedDocument).isEqualTo(preparedDocument);
    }

    @DisplayName("Should throw UserPreferenceNotFoundEx when document is not exists by userId")
    @Test
    void getUserPreferenceByUserId_shouldThrowException() {
        var nonExistsUserId = UUID.randomUUID();
        assertThatThrownBy(() -> userPreferenceService.getUserPreferenceByUserId(nonExistsUserId))
                .isInstanceOf(UserPreferenceNotFoundException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should update notification preference by userId when document is exists")
    @Test
    void updateNotificationPreferenceByUserId_shouldUpdate() {
        var updateRequest = NotificationPreferenceUpdateRequestBuilder.builder().build();

        userPreferenceService.updateNotificationPreferenceByUserId(preparedDocument.getUserId(), updateRequest);

        var userPreferenceOptional = userPreferenceRepository.findAll().stream()
                        .findFirst();
        assertThat(userPreferenceOptional).isPresent();
        var notificationPreference = userPreferenceOptional.get()
                .getNotificationPreference();
        assertThat(notificationPreference.getNotificationChannels()).isEqualTo(updateRequest.notificationChannels());
    }

    @DisplayName("Should throw UserPreferenceNotFoundEx and do not update notification preference by userId ")
    @Test
    void updateNotificationPreferenceByUserId_shouldThrowException() {
        var nonExistsUserId = UUID.randomUUID();
        var updateRequest = NotificationPreferenceUpdateRequestBuilder.builder().build();

        assertThatThrownBy(() -> userPreferenceService.updateNotificationPreferenceByUserId(nonExistsUserId, updateRequest))
                .isInstanceOf(UserPreferenceNotFoundException.class)
                .message().isNotBlank();
    }

    private UserPreferenceDocument insertUserPreferenceDocument() {
        var document = UserPreferenceDocumentBuilder.builder().build();
        return userPreferenceRepository.save(document);
    }

    private void deleteAllDocuments() {
        userPreferenceRepository.deleteAll();
    }
}
