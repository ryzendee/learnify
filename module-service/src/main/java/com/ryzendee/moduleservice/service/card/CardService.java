package com.ryzendee.moduleservice.service.card;

import com.ryzendee.moduleservice.dto.card.request.CardCreateRequest;
import com.ryzendee.moduleservice.dto.card.request.CardUpdateRequest;
import com.ryzendee.moduleservice.dto.card.response.CardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CardService {
    CardResponse createCard(UUID learningModuleId, CardCreateRequest request);
    CardResponse updateCardById(UUID id, CardUpdateRequest request);
    void deleteCardById(UUID id);
    CardResponse getCardById(UUID id);
    Page<CardResponse> getCardPageByLearningModuleId(UUID learningModuleId, Pageable pageable);

}
