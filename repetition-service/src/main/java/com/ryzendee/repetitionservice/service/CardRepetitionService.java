package com.ryzendee.repetitionservice.service;

import com.learnify.kafka.models.card.events.CardCreatedEvent;
import com.ryzendee.repetitionservice.dto.repetition.request.CardRepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.response.CardRepetitionGetResponse;

import java.util.List;
import java.util.UUID;

public interface CardRepetitionService {

    void createCardRepetition(CardCreatedEvent event);
    void updateCardRepetitionByCardId(UUID cardId, CardRepetitionUpdateRequest request);
    void deleteCardRepetitionByCardId(UUID cardId);
    void deleteAllCardRepetitionsByLearningModuleId(UUID learningModuleId);
    List<CardRepetitionGetResponse> getCardsForRepetitionByLearningModuleId(UUID learningModuleID);
}
