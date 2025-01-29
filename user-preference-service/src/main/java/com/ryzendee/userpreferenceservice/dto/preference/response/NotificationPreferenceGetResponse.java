package com.ryzendee.userpreferenceservice.dto.preference.response;

import com.ryzendee.userpreferenceservice.document.notification.NotificationChannel;
import com.ryzendee.userpreferenceservice.document.notification.NotificationType;

import java.util.List;
import java.util.Map;

public record NotificationPreferenceGetResponse(Map<NotificationType, List<NotificationChannel>> notificationChannels) {
}
