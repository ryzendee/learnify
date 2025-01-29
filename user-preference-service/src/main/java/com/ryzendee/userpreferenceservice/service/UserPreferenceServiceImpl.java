package com.ryzendee.userpreferenceservice.service;

import com.ryzendee.userpreferenceservice.document.notification.NotificationPreference;
import com.ryzendee.userpreferenceservice.document.UserPreferenceDocument;
import com.ryzendee.userpreferenceservice.dto.preference.request.NotificationPreferenceUpdateRequest;
import com.ryzendee.userpreferenceservice.dto.preference.response.UserPreferenceGetResponse;
import com.ryzendee.userpreferenceservice.exception.UserPreferenceNotFoundException;
import com.ryzendee.userpreferenceservice.mapper.UserPreferenceGetResponseMapper;
import com.ryzendee.userpreferenceservice.repository.UserPreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserPreferenceGetResponseMapper userPreferenceGetResponseMapper;

    @Override
    public UserPreferenceGetResponse getUserPreferenceByUserId(UUID userId) {
        UserPreferenceDocument document = findUserPreferenceByUserId(userId);

        return userPreferenceGetResponseMapper.map(document);
    }

    @Override
    public UserPreferenceGetResponse updateNotificationPreferenceByUserId(UUID userId,
                                                                          NotificationPreferenceUpdateRequest request) {
        UserPreferenceDocument document = findUserPreferenceByUserId(userId);
        document.setNotificationPreference(new NotificationPreference(request.notificationChannels()));
        userPreferenceRepository.save(document);
        log.info("Notification preferences was updated for user: {}", document.getUserId());

        return userPreferenceGetResponseMapper.map(document);
    }

    private UserPreferenceDocument findUserPreferenceByUserId(UUID userId) {
        return userPreferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new UserPreferenceNotFoundException("User preference with given id does not exists"));
    }
}
