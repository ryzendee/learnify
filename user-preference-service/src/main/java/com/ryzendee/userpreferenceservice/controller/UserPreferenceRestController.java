package com.ryzendee.userpreferenceservice.controller;

import com.ryzendee.userpreferenceservice.document.notification.NotificationChannel;
import com.ryzendee.userpreferenceservice.document.notification.NotificationType;
import com.ryzendee.userpreferenceservice.dto.options.response.NotificationOptionsResponse;
import com.ryzendee.userpreferenceservice.dto.preference.request.NotificationPreferenceUpdateRequest;
import com.ryzendee.userpreferenceservice.dto.preference.response.UserPreferenceGetResponse;
import com.ryzendee.userpreferenceservice.service.UserPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-preferences")
@RequiredArgsConstructor
public class UserPreferenceRestController {

    private final UserPreferenceService userPreferenceService;

    @GetMapping("/{userId}")
    public UserPreferenceGetResponse getUserPreferenceByUserId(@PathVariable UUID userId) {
        return userPreferenceService.getUserPreferenceByUserId(userId);
    }

    @PatchMapping("/{userId}/notification-preferences")
    public UserPreferenceGetResponse updateNotificationPreferencesByUserId(
            @PathVariable UUID userId,
            @Valid @RequestBody NotificationPreferenceUpdateRequest request) {
        return userPreferenceService.updateNotificationPreferenceByUserId(userId, request);
    }

    @GetMapping("/notification-options")
    public NotificationOptionsResponse getNotificationOptions() {
        List<NotificationType> notificationTypes = Arrays.asList(NotificationType.values());
        List<NotificationChannel> notificationChannels = Arrays.asList(NotificationChannel.values());
        return new NotificationOptionsResponse(notificationTypes, notificationChannels);
    }

}
