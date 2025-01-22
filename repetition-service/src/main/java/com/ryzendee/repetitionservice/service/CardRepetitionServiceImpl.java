package com.ryzendee.repetitionservice.service;

import com.learnify.kafka.models.card.events.CardCreatedEvent;
import com.ryzendee.repetitionservice.dto.repetition.request.CardRepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.request.RepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.response.CardRepetitionGetResponse;
import com.ryzendee.repetitionservice.dto.repetition.response.RepetitionUpdateResponse;
import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.exception.CardRepetitionNotFoundException;
import com.ryzendee.repetitionservice.exception.CardRepetitionSaveException;
import com.ryzendee.repetitionservice.mapper.CardRepetitionEntityMapper;
import com.ryzendee.repetitionservice.mapper.CardRepetitionGetResponseMapper;
import com.ryzendee.repetitionservice.mapper.RepetitionUpdateRequestMapper;
import com.ryzendee.repetitionservice.repository.CardRepetitionJpaRepository;
import com.ryzendee.repetitionservice.service.helpers.calculator.RepetitionCalculator;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardRepetitionServiceImpl implements CardRepetitionService {

    private final CardRepetitionJpaRepository cardRepetitionJpaRepository;
    private final RepetitionCalculator repetitionCalculator;
    private final CardRepetitionEntityMapper cardRepetitionEntityMapper;
    private final CardRepetitionGetResponseMapper cardRepetitionGetResponseMapper;
    private final RepetitionUpdateRequestMapper repetitionUpdateRequestMapper;

    @Transactional
    @Override
    public void createCardRepetition(CardCreatedEvent event) {
        CardRepetitionEntity mappedEntity = cardRepetitionEntityMapper.map(event);

        try {
            cardRepetitionJpaRepository.save(mappedEntity);
        } catch (ConstraintViolationException ex) {
            log.warn("Failed to save card repetition", ex);
            throw new CardRepetitionSaveException("Failed to save card repetition", ex);
        }

        log.info("Created card repetition: {}", mappedEntity.getId());
    }

    @Transactional
    @Override
    public void updateCardRepetitionByCardId(UUID cardId, CardRepetitionUpdateRequest request) {
        log.info("Updating card repetition by card id: {}", cardId);
        CardRepetitionEntity entityToUpdate = getCardRepetitionByCardId(cardId);
        RepetitionUpdateRequest repetitionUpdateRequest = repetitionUpdateRequestMapper.map(entityToUpdate, request.reviewRating());
        RepetitionUpdateResponse repetitionUpdateResponse = repetitionCalculator.calculate(repetitionUpdateRequest);

        entityToUpdate.setLastRepetitionDate(LocalDateTime.now());
        entityToUpdate.setEaseFactor(repetitionUpdateResponse.updatedEaseFactor());
        entityToUpdate.setNextRepetitionDate(repetitionUpdateResponse.nextRepetitionDate());
        entityToUpdate.setDayInterval(repetitionUpdateResponse.updatedDayInterval());
        entityToUpdate.setRepetitionCount(repetitionUpdateResponse.updatedRepetitionCount());

        cardRepetitionJpaRepository.save(entityToUpdate);
    }

    @Transactional
    @Override
    public void deleteCardRepetitionByCardId(UUID cardId) {
        log.info("Deleting card repetition with card id: {}", cardId);
        CardRepetitionEntity entityToDelete = getCardRepetitionByCardId(cardId);
        cardRepetitionJpaRepository.delete(entityToDelete);
    }

    @Transactional
    @Override
    public void deleteAllCardRepetitionsByLearningModuleId(UUID learningModuleId) {
        log.info("Deleting all card repetitions with learning module id: {}", learningModuleId);
        cardRepetitionJpaRepository.deleteAllByLearningModuleId(learningModuleId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CardRepetitionGetResponse> getCardsForRepetitionByLearningModuleId(UUID learningModuleId) {
        log.info("Returning card repetitions for learning module id: {}", learningModuleId);
        return cardRepetitionJpaRepository.findActualRepetitionsByLearningModuleId(learningModuleId).stream()
                .map(cardRepetitionGetResponseMapper::map)
                .toList();
    }

    private CardRepetitionEntity getCardRepetitionByCardId(UUID cardId) {
        return cardRepetitionJpaRepository.findByCardId(cardId)
                .orElseThrow(() -> new CardRepetitionNotFoundException("Card repetition for given cardId does not exists"));
    }
}
