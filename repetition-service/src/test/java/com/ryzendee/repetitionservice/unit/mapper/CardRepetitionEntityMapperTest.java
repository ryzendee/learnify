package com.ryzendee.repetitionservice.unit.mapper;

import com.learnify.kafka.models.card.events.CardCreatedEvent;
import com.ryzendee.repetitionservice.mapper.CardRepetitionEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CardRepetitionEntityMapperTest {

    private CardRepetitionEntityMapper cardRepetitionEntityMapper;

    @BeforeEach
    void setUp() {
        cardRepetitionEntityMapper = Mappers.getMapper(CardRepetitionEntityMapper.class);
    }

    @Test
    void map() {
        var cardCreatedEvent = new CardCreatedEvent(UUID.randomUUID(), UUID.randomUUID());

        var cardRepetitionEntity = cardRepetitionEntityMapper.map(cardCreatedEvent);
        assertThat(cardRepetitionEntity.getCardId()).isEqualTo(cardCreatedEvent.getCardId());
        assertThat(cardRepetitionEntity.getLearningModuleId()).isEqualTo(cardCreatedEvent.getLearningModuleId());
    }
}
