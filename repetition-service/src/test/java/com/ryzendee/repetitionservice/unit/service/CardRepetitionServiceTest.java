package com.ryzendee.repetitionservice.unit.service;

import com.learnify.kafka.models.card.events.CardCreatedEvent;
import com.ryzendee.repetitionservice.dto.repetition.request.CardRepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.response.CardRepetitionGetResponse;
import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import com.ryzendee.repetitionservice.exception.CardRepetitionNotFoundException;
import com.ryzendee.repetitionservice.exception.CardRepetitionSaveException;
import com.ryzendee.repetitionservice.mapper.CardRepetitionEntityMapper;
import com.ryzendee.repetitionservice.mapper.CardRepetitionGetResponseMapper;
import com.ryzendee.repetitionservice.mapper.RepetitionUpdateRequestMapper;
import com.ryzendee.repetitionservice.repository.CardRepetitionJpaRepository;
import com.ryzendee.repetitionservice.service.CardRepetitionServiceImpl;
import com.ryzendee.repetitionservice.service.helpers.calculator.RepetitionCalculator;
import com.ryzendee.repetitionservice.testutils.builder.entity.CardRepetitionEntityBuilder;
import com.ryzendee.repetitionservice.testutils.builder.entity.RepetitionUpdateRequestBuilder;
import com.ryzendee.repetitionservice.testutils.builder.entity.RepetitionUpdateResponseBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardRepetitionServiceTest {

    @InjectMocks
    private CardRepetitionServiceImpl cardRepetitionService;

    @Mock
    private CardRepetitionJpaRepository cardRepetitionJpaRepository;
    @Mock
    private RepetitionCalculator repetitionCalculator;
    @Mock
    private CardRepetitionEntityMapper cardRepetitionEntityMapper;
    @Mock
    private CardRepetitionGetResponseMapper cardRepetitionGetResponseMapper;
    @Mock
    private RepetitionUpdateRequestMapper repetitionUpdateRequestMapper;

    private CardRepetitionEntity cardRepetitionEntity;
    private UUID cardId;
    private UUID learningModuleId;

    @BeforeEach
    void setUp() {
        cardRepetitionEntity = CardRepetitionEntityBuilder.builder().build();
        cardId = UUID.randomUUID();
        learningModuleId = UUID.randomUUID();
    }

    @DisplayName("Should create card repetition")
    @Test
    void createCardRepetition_shouldCreateCardRepetition() {
        var cardCreatedEvent = new CardCreatedEvent(cardId, learningModuleId);

        when(cardRepetitionEntityMapper.map(cardCreatedEvent)).thenReturn(cardRepetitionEntity);
        when(cardRepetitionJpaRepository.save(cardRepetitionEntity)).thenReturn(cardRepetitionEntity);

        cardRepetitionService.createCardRepetition(cardCreatedEvent);

        verify(cardRepetitionEntityMapper).map(cardCreatedEvent);
        verify(cardRepetitionJpaRepository).save(cardRepetitionEntity);
    }

    @DisplayName("Should throw CardRepetitionSaveException when repository throws exception while creating card")
    @Test
    void createCardRepetition_shouldThrowCardRepetitionSaveEx() {
        var cardCreatedEvent = new CardCreatedEvent(cardId, learningModuleId);

        when(cardRepetitionEntityMapper.map(cardCreatedEvent)).thenReturn(cardRepetitionEntity);
        doThrow(DataIntegrityViolationException.class)
                .when(cardRepetitionJpaRepository).save(cardRepetitionEntity);

        assertThatThrownBy(() -> cardRepetitionService.createCardRepetition(cardCreatedEvent))
                .isInstanceOf(CardRepetitionSaveException.class)
                .message().isNotBlank();

        verify(cardRepetitionEntityMapper).map(cardCreatedEvent);
    }

    @DisplayName("Should update card repetition")
    @Test
    void updateCardRepetitionByCardId_shouldUpdate() {
        // given
        var cardRepetitionUpdateRequest = new CardRepetitionUpdateRequest(ReviewRating.EASY);
        var repetitionUpdateRequest = RepetitionUpdateRequestBuilder.builder().build();
        var repetitionUpdateResponse = RepetitionUpdateResponseBuilder.builder().build();
        var currentLastRepetitionDate = cardRepetitionEntity.getLastRepetitionDate();

        when(cardRepetitionJpaRepository.findByCardId(cardId))
                .thenReturn(Optional.of(cardRepetitionEntity));
        when(repetitionUpdateRequestMapper.map(cardRepetitionEntity, cardRepetitionUpdateRequest.reviewRating()))
                .thenReturn(repetitionUpdateRequest);
        when(repetitionCalculator.calculate(repetitionUpdateRequest))
                .thenReturn(repetitionUpdateResponse);
        when(cardRepetitionJpaRepository.save(cardRepetitionEntity))
                .thenReturn(cardRepetitionEntity);

        // when
        cardRepetitionService.updateCardRepetitionByCardId(cardId, cardRepetitionUpdateRequest);

        // then
        verify(cardRepetitionJpaRepository).findByCardId(cardId);
        verify(repetitionUpdateRequestMapper).map(cardRepetitionEntity, cardRepetitionUpdateRequest.reviewRating());
        verify(repetitionCalculator).calculate(repetitionUpdateRequest);
        verify(cardRepetitionJpaRepository).save(cardRepetitionEntity);

        assertThat(currentLastRepetitionDate).isAfterOrEqualTo(currentLastRepetitionDate);
        assertThat(cardRepetitionEntity.getEaseFactor()).isEqualTo(repetitionUpdateResponse.updatedEaseFactor());
        assertThat(cardRepetitionEntity.getNextRepetitionDate()).isEqualTo(repetitionUpdateResponse.nextRepetitionDate());
        assertThat(cardRepetitionEntity.getDayInterval()).isEqualTo(repetitionUpdateResponse.updatedDayInterval());
        assertThat(cardRepetitionEntity.getRepetitionCount()).isEqualTo(repetitionUpdateResponse.updatedRepetitionCount());
    }

    @DisplayName("Should throw CardRepetitionSaveException when repository throws exception while updating card")
    @Test
    void updateCardRepetitionByCardId_shouldThrowCardRepetitionSaveEx() {
        //given
        var cardRepetitionUpdateRequest = new CardRepetitionUpdateRequest(ReviewRating.EASY);
        var repetitionUpdateRequest = RepetitionUpdateRequestBuilder.builder().build();
        var repetitionUpdateResponse = RepetitionUpdateResponseBuilder.builder().build();

        when(cardRepetitionJpaRepository.findByCardId(cardId))
                .thenReturn(Optional.of(cardRepetitionEntity));
        when(repetitionUpdateRequestMapper.map(cardRepetitionEntity, cardRepetitionUpdateRequest.reviewRating()))
                .thenReturn(repetitionUpdateRequest);
        when(repetitionCalculator.calculate(repetitionUpdateRequest))
                .thenReturn(repetitionUpdateResponse);
        doThrow(DataIntegrityViolationException.class)
                .when(cardRepetitionJpaRepository).save(cardRepetitionEntity);

        // when & then
        assertThatThrownBy(() -> cardRepetitionService.updateCardRepetitionByCardId(cardId, cardRepetitionUpdateRequest))
                .isInstanceOf(CardRepetitionSaveException.class)
                .message().isNotBlank();

        verify(cardRepetitionJpaRepository).findByCardId(cardId);
        verify(repetitionUpdateRequestMapper).map(cardRepetitionEntity, cardRepetitionUpdateRequest.reviewRating());
        verify(repetitionCalculator).calculate(repetitionUpdateRequest);
    }

    @DisplayName("Should delete card repetition by id when entity exists")
    @Test
    void deleteCardRepetitionByCardId_shouldDelete() {
        when(cardRepetitionJpaRepository.findByCardId(cardId))
                .thenReturn(Optional.of(cardRepetitionEntity));

        cardRepetitionService.deleteCardRepetitionByCardId(cardId);

        verify(cardRepetitionJpaRepository).findByCardId(cardId);
        verify(cardRepetitionJpaRepository).delete(cardRepetitionEntity);
    }

    @DisplayName("Should throw CardRepetitionNotFoundException when trying to delete card that does not exist in db")
    @Test
    void deleteCardRepetitionByCardId_shouldThrowCardRepetitionNotFoundEx() {
        when(cardRepetitionJpaRepository.findByCardId(cardId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cardRepetitionService.deleteCardRepetitionByCardId(cardId))
                .isInstanceOf(CardRepetitionNotFoundException.class)
                .message().isNotBlank();

        verify(cardRepetitionJpaRepository).findByCardId(cardId);
        verify(cardRepetitionJpaRepository, never()).delete(any(CardRepetitionEntity.class));
    }

    @DisplayName("Should delete all card repetitions by given learning module id")
    @Test
    void deleteAllCardRepetitionsByLearningModuleId_shouldDeleteCardRepetitions() {
        cardRepetitionService.deleteAllCardRepetitionsByLearningModuleId(learningModuleId);

        verify(cardRepetitionJpaRepository).deleteAllByLearningModuleId(learningModuleId);
    }

    @DisplayName("Should return cards for repetitions by given learning module id")
    @Test
    void getCardsForRepetitionByLearningModuleId_shouldReturnCardRepetitions() {
        var expectedCardRepetitionGetResponse = new CardRepetitionGetResponse(cardId);

        when(cardRepetitionJpaRepository.findActualRepetitionsByLearningModuleId(learningModuleId))
                .thenReturn(List.of(cardRepetitionEntity));
        when(cardRepetitionGetResponseMapper.map(cardRepetitionEntity))
                .thenReturn(expectedCardRepetitionGetResponse);

        var listWithCardGetResponses = cardRepetitionService.getCardsForRepetitionByLearningModuleId(learningModuleId);

        verify(cardRepetitionJpaRepository).findActualRepetitionsByLearningModuleId(learningModuleId);
        verify(cardRepetitionGetResponseMapper).map(cardRepetitionEntity);

        assertThat(listWithCardGetResponses).containsOnly(expectedCardRepetitionGetResponse);
    }
}
