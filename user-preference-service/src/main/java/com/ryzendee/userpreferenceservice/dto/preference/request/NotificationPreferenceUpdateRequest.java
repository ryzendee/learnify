package com.ryzendee.userpreferenceservice.dto.preference.request;

import com.ryzendee.userpreferenceservice.document.notification.NotificationChannel;
import com.ryzendee.userpreferenceservice.document.notification.NotificationType;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Map;

public record NotificationPreferenceUpdateRequest (
        @NotEmpty(message = "Notification channels map must not be empty")
        Map<NotificationType, List<NotificationChannel>> notificationChannels
) {
}
