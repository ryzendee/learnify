package com.ryzendee.moduleservice.testutils.builder.learningmodule;

import com.ryzendee.moduleservice.dto.learningmodule.response.LearningModuleResponse;
import com.ryzendee.moduleservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.UUID;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class LearningModuleResponseBuilder implements TestBaseBuilder<LearningModuleResponse> {

    private UUID id = UUID.randomUUID();
    private String name = "learningModuleResponseName";
    private String description = "learningModuleResponseDescription";
    private String imageObjectName = "learningModuleResponseImageObjectName";

    @Override
    public LearningModuleResponse build() {
        return new LearningModuleResponse(
                id,
                name,
                description,
                imageObjectName
        );
    }
}
