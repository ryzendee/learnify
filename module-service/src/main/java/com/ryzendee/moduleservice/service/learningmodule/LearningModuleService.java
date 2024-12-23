package com.ryzendee.moduleservice.service.learningmodule;

import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleCreateRequest;
import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleUpdateRequest;
import com.ryzendee.moduleservice.dto.learningmodule.response.LearningModuleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LearningModuleService {

    LearningModuleResponse createLearningModule(LearningModuleCreateRequest request);
    LearningModuleResponse updateLearningModuleById(UUID id, LearningModuleUpdateRequest request);
    void deleteLearningModuleById(UUID id);
    LearningModuleResponse getLearningModuleById(UUID id);
    Page<LearningModuleResponse> getLearningPage(Pageable pageable);
}
