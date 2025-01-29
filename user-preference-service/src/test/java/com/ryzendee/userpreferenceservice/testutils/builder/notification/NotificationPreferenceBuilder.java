package com.ryzendee.userpreferenceservice.testutils.builder.notification;

import com.ryzendee.userpreferenceservice.document.notification.NotificationChannel;
import com.ryzendee.userpreferenceservice.document.notification.NotificationPreference;
import com.ryzendee.userpreferenceservice.document.notification.NotificationType;
import com.ryzendee.userpreferenceservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;
import java.util.Map;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class NotificationPreferenceBuilder implements TestBaseBuilder<NotificationPreference> {

    private Map<NotificationType, List<NotificationChannel>> notificationChannels
            = Map.of(NotificationType.NEW_CARD_TO_LEARN, List.of(NotificationChannel.EMAIL));

    @Override
    public NotificationPreference build() {
        return new NotificationPreference(notificationChannels);
    }
}
