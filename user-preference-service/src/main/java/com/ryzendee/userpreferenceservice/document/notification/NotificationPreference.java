package com.ryzendee.userpreferenceservice.document.notification;

import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPreference {

    private Map<NotificationType, List<NotificationChannel>> notificationChannels;

}
