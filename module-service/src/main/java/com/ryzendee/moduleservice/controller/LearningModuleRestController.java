package com.ryzendee.moduleservice.controller;

import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleCreateRequest;
import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleUpdateRequest;
import com.ryzendee.moduleservice.dto.learningmodule.response.LearningModuleResponse;
import com.ryzendee.moduleservice.service.learningmodule.LearningModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/learning-modules")
@RequiredArgsConstructor
public class LearningModuleRestController {

    private final LearningModuleService learningModuleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public LearningModuleResponse createModule(@Valid @RequestBody LearningModuleCreateRequest request) {
        return learningModuleService.createLearningModule(request);
    }

    @PutMapping("/{id}")
    public LearningModuleResponse updateModuleById(@Valid @RequestBody LearningModuleUpdateRequest request,
                                                   @PathVariable UUID id) {
        return learningModuleService.updateLearningModuleById(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteModuleById(@PathVariable UUID id) {
        learningModuleService.deleteLearningModuleById(id);
    }

    @GetMapping("/{id}")
    public LearningModuleResponse getModuleById(@PathVariable UUID id) {
        return learningModuleService.getLearningModuleById(id);
    }

    @GetMapping
    public Page<LearningModuleResponse> getModulePage(Pageable pageable) {
        return learningModuleService.getLearningPage(pageable);
    }
}
