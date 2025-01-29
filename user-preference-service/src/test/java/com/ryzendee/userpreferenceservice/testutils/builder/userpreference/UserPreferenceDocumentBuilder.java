package com.ryzendee.userpreferenceservice.testutils.builder.userpreference;

import com.ryzendee.userpreferenceservice.document.UserPreferenceDocument;
import com.ryzendee.userpreferenceservice.document.notification.NotificationPreference;
import com.ryzendee.userpreferenceservice.testutils.builder.TestBaseBuilder;
import com.ryzendee.userpreferenceservice.testutils.builder.notification.NotificationPreferenceBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.UUID;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class UserPreferenceDocumentBuilder implements TestBaseBuilder<UserPreferenceDocument> {

    private String id = null;
    private UUID userId = UUID.randomUUID();
    private NotificationPreference notificationPreference = NotificationPreferenceBuilder.builder().build();

    @Override
    public UserPreferenceDocument build() {
        return UserPreferenceDocument.builder()
                .id(id)
                .userId(userId)
                .notificationPreference(notificationPreference)
                .build();
    }
}
