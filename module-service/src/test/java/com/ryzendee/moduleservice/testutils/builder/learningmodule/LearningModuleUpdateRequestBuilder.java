package com.ryzendee.moduleservice.testutils.builder.learningmodule;


import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleUpdateRequest;
import com.ryzendee.moduleservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class LearningModuleUpdateRequestBuilder implements TestBaseBuilder<LearningModuleUpdateRequest> {

    private String name = "updateRequestName";
    private String description = "updateRequestDescription";
    private String imageObjectName = "updateRequestImageObjectName";

    @Override
    public LearningModuleUpdateRequest build() {
        return new LearningModuleUpdateRequest(name, description, imageObjectName);
    }
}
