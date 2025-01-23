package com.ryzendee.repetitionservice.testutils.builder.entity;

import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CardRepetitionEntityBuilder implements TestBaseBuilder<CardRepetitionEntity> {

    private UUID id = null;
    private UUID cardId = UUID.randomUUID();
    private UUID learningModuleId = UUID.randomUUID();
    private double easeFactor = 2.5;
    private int repetitionCount = 0;
    private int dayInterval = 1;
    private LocalDateTime lastRepetitionDate = LocalDateTime.now();
    private LocalDateTime nextRepetitionDate = LocalDateTime.now();

    @Override
    public CardRepetitionEntity build() {
        return CardRepetitionEntity.builder()
                .id(id)
                .cardId(cardId)
                .learningModuleId(learningModuleId)
                .easeFactor(easeFactor)
                .repetitionCount(repetitionCount)
                .dayInterval(dayInterval)
                .lastRepetitionDate(lastRepetitionDate)
                .nextRepetitionDate(nextRepetitionDate)
                .build();
    }
}
