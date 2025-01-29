package com.ryzendee.userpreferenceservice.service;

import com.ryzendee.userpreferenceservice.dto.preference.request.NotificationPreferenceUpdateRequest;
import com.ryzendee.userpreferenceservice.dto.preference.response.UserPreferenceGetResponse;

import java.util.UUID;

public interface UserPreferenceService {

    UserPreferenceGetResponse getUserPreferenceByUserId(UUID userId);
    UserPreferenceGetResponse updateNotificationPreferenceByUserId(UUID userId, NotificationPreferenceUpdateRequest request);
}
