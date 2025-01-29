package com.ryzendee.userpreferenceservice.unit.mapper;

import com.ryzendee.userpreferenceservice.mapper.UserPreferenceGetResponseMapper;
import com.ryzendee.userpreferenceservice.testutils.builder.notification.NotificationPreferenceBuilder;
import com.ryzendee.userpreferenceservice.testutils.builder.userpreference.UserPreferenceDocumentBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class UserPreferenceGetResponseMapperTest {

    private UserPreferenceGetResponseMapper userPreferenceGetResponseMapper;

    @BeforeEach
    void setUp() {
        userPreferenceGetResponseMapper = Mappers.getMapper(UserPreferenceGetResponseMapper.class);
    }

    @Test
    void map() {
        var notificationPreference = NotificationPreferenceBuilder.builder().build();
        var userPreferenceDocument = UserPreferenceDocumentBuilder.builder()
                .withNotificationPreference(notificationPreference)
                .build();

        var userPreferenceGetResponse = userPreferenceGetResponseMapper.map(userPreferenceDocument);
        assertThat(userPreferenceGetResponse.userId()).isEqualTo(userPreferenceDocument.getUserId());

        var notificationPreferenceGetResponse = userPreferenceGetResponse.notificationPreference();
        assertThat(notificationPreferenceGetResponse.notificationChannels())
                .isEqualTo(notificationPreference.getNotificationChannels());
    }
}
