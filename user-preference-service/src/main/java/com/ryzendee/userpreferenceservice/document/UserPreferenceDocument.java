package com.ryzendee.userpreferenceservice.document;

import com.ryzendee.userpreferenceservice.document.notification.NotificationPreference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.UUID;

@Document("user_preferences")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceDocument {

    @Id
    private String id;
    @Field(targetType = FieldType.STRING)
    private UUID userId;

    @EqualsAndHashCode.Exclude
    private NotificationPreference notificationPreference;
}

