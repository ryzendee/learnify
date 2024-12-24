package com.ryzendee.moduleservice.testutils.builder.card;

import com.ryzendee.moduleservice.entity.CardEntity;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import com.ryzendee.moduleservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.UUID;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CardEntityBuilder implements TestBaseBuilder<CardEntity> {

    private UUID id = null;
    private String term = "cardEntityTerm";
    private String definition = "cardDefinition";

    private LearningModuleEntity learningModuleEntity = null;

    @Override
    public CardEntity build() {
        return CardEntity.builder()
                .id(id)
                .term(term)
                .definition(definition)
                .learningModuleEntity(learningModuleEntity)
                .build();
    }
}
