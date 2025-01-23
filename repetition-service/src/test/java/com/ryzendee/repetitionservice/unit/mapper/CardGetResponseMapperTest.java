package com.ryzendee.repetitionservice.unit.mapper;

import com.ryzendee.repetitionservice.mapper.CardRepetitionGetResponseMapper;
import com.ryzendee.repetitionservice.testutils.builder.entity.CardRepetitionEntityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CardGetResponseMapperTest {

    private CardRepetitionGetResponseMapper cardRepetitionGetResponseMapper;

    @BeforeEach
    void setUp() {
        cardRepetitionGetResponseMapper = Mappers.getMapper(CardRepetitionGetResponseMapper.class);
    }

    @Test
    void test() {
        var cardRepetitionEntity = CardRepetitionEntityBuilder.builder()
                .withCardId(UUID.randomUUID())
                .build();

        var cardGetResponse = cardRepetitionGetResponseMapper.map(cardRepetitionEntity);
        assertThat(cardGetResponse.cardId()).isEqualTo(cardRepetitionEntity.getCardId());
    }
}
