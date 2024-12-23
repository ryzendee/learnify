package com.ryzendee.moduleservice.testutils.builder.learningmodule;

import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleCreateRequest;
import com.ryzendee.moduleservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class LearningModuleCreateRequestBuilder implements TestBaseBuilder<LearningModuleCreateRequest> {

    private String name = "createRequestName";
    private String description = "createRequestDescription";

    @Override
    public LearningModuleCreateRequest build() {
        return new LearningModuleCreateRequest(name, description);
    }
}
