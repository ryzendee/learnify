package com.ryzendee.repetitionservice.unit.mapper;

import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import com.ryzendee.repetitionservice.mapper.RepetitionUpdateRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class RepetitionUpdateRequestMapperTest {

    private RepetitionUpdateRequestMapper repetitionUpdateRequestMapper;

    @BeforeEach
    void setUp() {
        repetitionUpdateRequestMapper = Mappers.getMapper(RepetitionUpdateRequestMapper.class);
    }

    @Test
    void map() {
        var cardRepetitionEntity = CardRepetitionEntity.builder().build();
        var reviewRating = ReviewRating.EASY;

        var repetitionUpdateRequest = repetitionUpdateRequestMapper.map(cardRepetitionEntity, reviewRating);
        assertThat(repetitionUpdateRequest.reviewRating()).isEqualTo(reviewRating);
        assertThat(repetitionUpdateRequest.repetitionCount()).isEqualTo(cardRepetitionEntity.getRepetitionCount());
        assertThat(repetitionUpdateRequest.dayInterval()).isEqualTo(cardRepetitionEntity.getDayInterval());
        assertThat(repetitionUpdateRequest.easeFactor()).isEqualTo(cardRepetitionEntity.getEaseFactor());
    }
}
