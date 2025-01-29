package com.ryzendee.userpreferenceservice.dto.options.response;

import com.ryzendee.userpreferenceservice.document.notification.NotificationChannel;
import com.ryzendee.userpreferenceservice.document.notification.NotificationType;

import java.util.List;

public record NotificationOptionsResponse(
        List<NotificationType> notificationTypes,
        List<NotificationChannel> notificationChannels
) {
}
