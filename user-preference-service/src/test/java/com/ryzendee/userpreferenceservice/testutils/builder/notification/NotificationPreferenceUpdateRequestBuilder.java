package com.ryzendee.userpreferenceservice.testutils.builder.notification;

import com.ryzendee.userpreferenceservice.dto.preference.request.NotificationPreferenceUpdateRequest;
import com.ryzendee.userpreferenceservice.document.notification.NotificationType;
import com.ryzendee.userpreferenceservice.document.notification.NotificationChannel;
import com.ryzendee.userpreferenceservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;
import java.util.Map;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class NotificationPreferenceUpdateRequestBuilder implements TestBaseBuilder<NotificationPreferenceUpdateRequest> {

    private Map<NotificationType, List<NotificationChannel>> notificationChannels
            = Map.of(NotificationType.NEW_CARD_TO_LEARN, List.of(NotificationChannel.EMAIL));

    @Override
    public NotificationPreferenceUpdateRequest build() {
        return new NotificationPreferenceUpdateRequest(notificationChannels);
    }
}
