package com.ryzendee.moduleservice.integration.service;

import com.ryzendee.moduleservice.entity.CardEntity;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import com.ryzendee.moduleservice.exception.CardNotFoundException;
import com.ryzendee.moduleservice.exception.LearningModuleNotFoundException;
import com.ryzendee.moduleservice.mapper.card.CardCreateRequestMapper;
import com.ryzendee.moduleservice.mapper.card.CardEntityMapper;
import com.ryzendee.moduleservice.service.card.CardService;
import com.ryzendee.moduleservice.testutils.builder.card.CardCreateRequestBuilder;
import com.ryzendee.moduleservice.testutils.builder.card.CardEntityBuilder;
import com.ryzendee.moduleservice.testutils.builder.card.CardResponseBuilder;
import com.ryzendee.moduleservice.testutils.builder.card.CardUpdateRequestBuilder;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleEntityBuilder;
import com.ryzendee.moduleservice.testutils.base.BaseServiceDbIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CardServiceDbIT extends BaseServiceDbIT {

    @Autowired
    private CardService cardService;
    @MockBean
    private CardCreateRequestMapper cardCreateRequestMapper;
    @MockBean
    private CardEntityMapper cardEntityMapper;
    private LearningModuleEntity preparedLearningModuleEntity;
    private CardEntity preparedCardEntity;

    @BeforeEach
    void setUpDatabaseData() {
        cleanDatabaseData();
        insertTestData();
    }

    @DisplayName("Should save card in database")
    @Test
    void createCard_withLearningModuleInDb_shouldSaveCard() {
        // Given
        var createRequest = CardCreateRequestBuilder.builder().build();
        var entityFromCreateRequest = CardEntityBuilder.builder().build();
        when(cardCreateRequestMapper.map(createRequest)).thenReturn(entityFromCreateRequest);

        // Act
        cardService.createCard(preparedLearningModuleEntity.getId(), createRequest);

        // Assert
        var savedEntity = testDatabaseFacade.find(entityFromCreateRequest.getId(), CardEntity.class);
        verify(cardCreateRequestMapper).map(createRequest);
        assertThat(savedEntity).isEqualTo(entityFromCreateRequest);
        assertThat(savedEntity.getCreatedAt()).isNotNull();
    }

    @DisplayName("Should throw LearningModuleNotFoundException when creating card without Learning Module in DB")
    @Test
    void createCard_withoutLearningModuleInDb_shouldThrowLearningModuleNotFoundEx() {
        var createRequest = CardCreateRequestBuilder.builder().build();

        assertThatThrownBy(() -> cardService.createCard(UUID.randomUUID(), createRequest))
                .isInstanceOf(LearningModuleNotFoundException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should update card in database")
    @Test
    void updateCardById_withCardInDb_shouldUpdateCard() {
        // Given
        var updateRequest = CardUpdateRequestBuilder.builder().build();

        // Act
        cardService.updateCardById(preparedCardEntity.getId(), updateRequest);

        // Assert
        var updatedEntity = testDatabaseFacade.find(preparedCardEntity.getId(), CardEntity.class);
        assertThat(updatedEntity.getTerm()).isEqualTo(updateRequest.term());
        assertThat(updatedEntity.getDefinition()).isEqualTo(updateRequest.definition());
        assertThat(updatedEntity.getUpdatedAt()).isNotNull();
    }

    @DisplayName("Should throw CardNotFoundException when updating card without card in DB")
    @Test
    void updateCardById_withoutCardInDb_shouldThrowCardNotFoundEx() {
        var updateRequest = CardUpdateRequestBuilder.builder().build();

        assertThatThrownBy(
                () -> cardService.updateCardById(UUID.randomUUID(), updateRequest))
                .isInstanceOf(CardNotFoundException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should return card response when card exists in DB")
    @Test
    void getCardById_withModuleInDb_shouldReturnModule() {
        var expectedCardResponse = CardResponseBuilder.builder().build();
        when(cardEntityMapper.map(preparedCardEntity))
                .thenReturn(expectedCardResponse);

        var actualResponse = cardService.getCardById(preparedCardEntity.getId());
        verify(cardEntityMapper).map(preparedCardEntity);
        assertThat(actualResponse).isEqualTo(expectedCardResponse);
    }

    @DisplayName("Should throw CardNotFoundException when card does not exist in DB")
    @Test
    void getCardById_withoutModuleInDb_shouldThrowCardNotFoundEx() {
        assertThatThrownBy(() -> cardService.getCardById(UUID.randomUUID()))
                .isInstanceOf(CardNotFoundException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should delete card from db")
    @Test
    void deleteCardById_withCardInDb_shouldDeleteCard() {
        cardService.deleteCardById(preparedCardEntity.getId());

        var entity = testDatabaseFacade.find(preparedCardEntity.getId(), CardEntity.class);
        assertThat(entity).isNull();
    }

    @DisplayName("Should throw CardNotFoundException when trying to delete card that does not exist in DB")
    @Test
    void deleteCardById_withoutCardInDb_shouldThrowCardNotFoundException() {
        assertThatThrownBy(
                () -> cardService.deleteCardById(UUID.randomUUID()))
                .isInstanceOf(CardNotFoundException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should return a page of cards for a given Learning Module id")
    @Test
    void getCardPageByLearningModuleId_withCardInDb_shouldReturnPage() {
        // Given
        var pageable = PageRequest.of(0, 1);
        var expectedCardResponse = CardResponseBuilder.builder().build();

        when(cardEntityMapper.map(preparedCardEntity)).thenReturn(expectedCardResponse);

        // Act
        var cardResponsePage =
                cardService.getCardPageByLearningModuleId(preparedLearningModuleEntity.getId(), pageable);

        // Assert
        assertThat(cardResponsePage).containsOnly(expectedCardResponse);
    }

    @DisplayName("Should throw PropertyReferenceException when invalid sorting property is provided")
    @Test
    void getCardPageByLearningModuleId_invalidSortingProperty_shouldThrowPropertyReferenceEx() {
        var sort = Sort.by("dummy");
        var pageable = PageRequest.of(0, 1, sort);

        assertThatThrownBy(
                () -> cardService.getCardPageByLearningModuleId(preparedLearningModuleEntity.getId(), pageable))
                .isInstanceOf(PropertyReferenceException.class)
                .message().isNotBlank();
    }

    private void insertTestData() {
        preparedLearningModuleEntity = testDatabaseFacade.save(LearningModuleEntityBuilder.builder());

        var cardEntityBuilder = CardEntityBuilder.builder()
                .withLearningModuleEntity(preparedLearningModuleEntity);
        preparedCardEntity = testDatabaseFacade.save(cardEntityBuilder);
    }

    private void cleanDatabaseData() {
        testDatabaseFacade.cleanDatabase();
    }
}
