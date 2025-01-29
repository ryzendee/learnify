package com.ryzendee.userpreferenceservice.repository;

import com.ryzendee.userpreferenceservice.document.UserPreferenceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceRepository extends MongoRepository<UserPreferenceDocument, String> {

    Optional<UserPreferenceDocument> findByUserId(UUID userId);
}
