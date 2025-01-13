package com.ryzendee.moduleservice.testutils.builder.learningmodule;

import com.ryzendee.moduleservice.entity.CardEntity;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import com.ryzendee.moduleservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class LearningModuleEntityBuilder implements TestBaseBuilder<LearningModuleEntity> {

    private UUID id = null;
    private String name = "entityName";
    private String description = "entityDescription";
    private String imageObjectName = "entityImageObjectName";
    private List<CardEntity> cardEntityList = new ArrayList<>();

    @Override
    public LearningModuleEntity build() {
        return LearningModuleEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .imageObjectName(imageObjectName)
                .cardEntityList(cardEntityList)
                .build();
    }
}
