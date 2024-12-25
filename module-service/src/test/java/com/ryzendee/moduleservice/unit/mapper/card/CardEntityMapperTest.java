package com.ryzendee.moduleservice.unit.mapper.card;

import com.ryzendee.moduleservice.mapper.card.CardEntityMapper;
import com.ryzendee.moduleservice.testutils.builder.card.CardEntityBuilder;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleEntityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CardEntityMapperTest {

    private CardEntityMapper cardEntityMapper;

    @BeforeEach
    void setUp() {
        cardEntityMapper = Mappers.getMapper(CardEntityMapper.class);
    }

    @Test
    void map() {
        var learningModule = LearningModuleEntityBuilder.builder()
                .withId(UUID.randomUUID())
                .build();
        var cardEntity = CardEntityBuilder.builder().build();
        learningModule.addCard(cardEntity);

        var mappedCardResponse = cardEntityMapper.map(cardEntity);
        assertThat(mappedCardResponse.id()).isEqualTo(cardEntity.getId());
        assertThat(mappedCardResponse.term()).isEqualTo(cardEntity.getTerm());
        assertThat(mappedCardResponse.definition()).isEqualTo(cardEntity.getDefinition());
        assertThat(mappedCardResponse.learningModuleId()).isEqualTo(learningModule.getId());
    }
}
