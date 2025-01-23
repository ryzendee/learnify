package com.ryzendee.repetitionservice.IT.repository;

import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.repository.CardRepetitionJpaRepository;
import com.ryzendee.repetitionservice.testutils.base.BaseTestcontainersTest;
import com.ryzendee.repetitionservice.testutils.builder.entity.CardRepetitionEntityBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardRepetitionJpaRepositoryIT extends BaseTestcontainersTest {

    @Autowired
    private CardRepetitionJpaRepository cardRepetitionJpaRepository;

    private CardRepetitionEntity preparedCardRepetitionEntity;

    @BeforeAll
    static void startUpPostgresContainer() {
        postgresContainer.start();
    }

    @BeforeEach
    void setUp() {
        deleteTestData();
        preparedCardRepetitionEntity = insertTestCardRepetitionEntity();
    }

    @DisplayName("Should return entity when finding actual repetitions by learning module ID")
    @Test
    void findActualRepetitionsByLearningModuleId_shouldReturnEntity() {
        var actualRepetitionEntity = CardRepetitionEntityBuilder.builder()
                .withNextRepetitionDate(LocalDateTime.now().minusHours(1))
                .build();
        cardRepetitionJpaRepository.save(actualRepetitionEntity);

        var actualRepetitionsList
                = cardRepetitionJpaRepository.findActualRepetitionsByLearningModuleId(actualRepetitionEntity.getLearningModuleId());

        assertThat(actualRepetitionsList).containsOnly(actualRepetitionEntity);
    }

    @DisplayName("Should return empty list when next repetition date is in the future")
    @Test
    void findActualRepetitionsByLearningModuleId_shouldReturnEmptyList() {
        preparedCardRepetitionEntity.setNextRepetitionDate(LocalDateTime.now().plusDays(1));
        cardRepetitionJpaRepository.save(preparedCardRepetitionEntity);

        var actualRepetitionsList
                = cardRepetitionJpaRepository.findActualRepetitionsByLearningModuleId(preparedCardRepetitionEntity.getLearningModuleId());

        assertThat(actualRepetitionsList).isEmpty();
    }

    @DisplayName("Should return entity when finding by card ID")
    @Test
    void findByCardId_shouldReturnEntity() {
        var actualEntityOptional
                = cardRepetitionJpaRepository.findByCardId(preparedCardRepetitionEntity.getCardId());

        assertThat(actualEntityOptional).isPresent();
        assertThat(actualEntityOptional.get()).isEqualTo(preparedCardRepetitionEntity);
    }

    @DisplayName("Should delete all entities by given learning module ID")
    @Test
    void deleteAllByLearningModuleId_shouldDeleteAllByGivenLearningModuleId() {
        cardRepetitionJpaRepository.deleteAllByLearningModuleId(preparedCardRepetitionEntity.getLearningModuleId());

        var entitiesFromDbList = cardRepetitionJpaRepository.findAll();
        assertThat(entitiesFromDbList).isEmpty();
    }

    private void deleteTestData() {
        cardRepetitionJpaRepository.deleteAll();
    }

    private CardRepetitionEntity insertTestCardRepetitionEntity() {
        var cardRepetitionEntity = CardRepetitionEntityBuilder.builder().build();
        return cardRepetitionJpaRepository.save(cardRepetitionEntity);
    }
}
