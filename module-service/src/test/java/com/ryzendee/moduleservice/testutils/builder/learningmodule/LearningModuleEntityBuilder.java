package com.ryzendee.moduleservice.testutils.builder.learningmodule;

import com.ryzendee.moduleservice.entity.CardEntity;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import com.ryzendee.moduleservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class LearningModuleEntityBuilder implements TestBaseBuilder<LearningModuleEntity> {

    private String name = "entityName";
    private String description = "entityDescription";
    private List<CardEntity> cardEntityList = new ArrayList<>();

    @Override
    public LearningModuleEntity build() {
        return LearningModuleEntity.builder()
                .name(name)
                .description(description)
                .cardEntityList(cardEntityList)
                .build();
    }
}
