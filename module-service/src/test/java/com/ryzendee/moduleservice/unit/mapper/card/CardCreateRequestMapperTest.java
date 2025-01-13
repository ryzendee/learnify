package com.ryzendee.moduleservice.unit.mapper.card;

import com.ryzendee.moduleservice.mapper.card.CardCreateRequestMapper;
import com.ryzendee.moduleservice.testutils.builder.card.CardCreateRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class CardCreateRequestMapperTest {

    private CardCreateRequestMapper cardCreateRequestMapper;

    @BeforeEach
    void setUp() {
        cardCreateRequestMapper = Mappers.getMapper(CardCreateRequestMapper.class);
    }

    @Test
    void map() {
        var cardCreateRequest = CardCreateRequestBuilder.builder().build();
        var mappedEntity = cardCreateRequestMapper.map(cardCreateRequest);

        assertThat(mappedEntity.getQuestion()).isEqualTo(cardCreateRequest.question());
        assertThat(mappedEntity.getAnswer()).isEqualTo(cardCreateRequest.answer());
    }
}
