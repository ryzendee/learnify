package com.ryzendee.userpreferenceservice.dto.preference.response;


import java.util.UUID;

public record UserPreferenceGetResponse(
        UUID userId,
        NotificationPreferenceGetResponse notificationPreference
) {
}
