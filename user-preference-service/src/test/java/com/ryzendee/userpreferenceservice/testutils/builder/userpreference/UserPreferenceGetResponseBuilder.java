package com.ryzendee.userpreferenceservice.testutils.builder.userpreference;

import com.ryzendee.userpreferenceservice.document.notification.NotificationPreference;
import com.ryzendee.userpreferenceservice.dto.preference.response.NotificationPreferenceGetResponse;
import com.ryzendee.userpreferenceservice.dto.preference.response.UserPreferenceGetResponse;
import com.ryzendee.userpreferenceservice.testutils.builder.TestBaseBuilder;
import com.ryzendee.userpreferenceservice.testutils.builder.notification.NotificationPreferenceGetResponseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.UUID;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class UserPreferenceGetResponseBuilder implements TestBaseBuilder<UserPreferenceGetResponse> {

    private UUID userId = UUID.randomUUID();
    private NotificationPreferenceGetResponse notificationPreference = NotificationPreferenceGetResponseBuilder.builder().build();

    @Override
    public UserPreferenceGetResponse build() {
        return new UserPreferenceGetResponse(userId, notificationPreference);
    }
}
