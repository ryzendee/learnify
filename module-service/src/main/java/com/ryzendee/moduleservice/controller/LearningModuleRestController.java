package com.ryzendee.moduleservice.controller;

import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleCreateRequest;
import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleUpdateRequest;
import com.ryzendee.moduleservice.dto.learningmodule.response.LearningModuleResponse;
import com.ryzendee.moduleservice.service.learningmodule.LearningModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Create a new learning module",
            description = "Creates a new learning module based on the provided request data.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Learning module created successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid request data.")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public LearningModuleResponse createModule(@Valid @RequestBody LearningModuleCreateRequest request) {
        return learningModuleService.createLearningModule(request);
    }

    @Operation(summary = "Update a learning module by ID",
            description = "Updates an existing learning module identified by its unique ID using the provided request data.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the learning module to update.", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Learning module updated successfully."),
                    @ApiResponse(responseCode = "404", description = "Learning module not found for the given ID."),
                    @ApiResponse(responseCode = "400", description = "Invalid request data.")
            })
    @PutMapping("/{id}")
    public LearningModuleResponse updateModuleById(@Valid @RequestBody LearningModuleUpdateRequest request,
                                                   @PathVariable UUID id) {
        return learningModuleService.updateLearningModuleById(id, request);
    }

    @Operation(summary = "Delete a learning module by ID",
            description = "Deletes a specific learning module identified by its unique ID.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the learning module to delete.", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Learning module deleted successfully."),
                    @ApiResponse(responseCode = "404", description = "Learning module not found for the given ID.")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteModuleById(@PathVariable UUID id) {
        learningModuleService.deleteLearningModuleById(id);
    }

    @Operation(summary = "Get a learning module by ID",
            description = "Retrieves a specific learning module based on its unique identifier.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the learning module to retrieve.", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Learning module retrieved successfully."),
                    @ApiResponse(responseCode = "404", description = "Learning module not found for the given ID.")
            })
    @GetMapping("/{id}")
    public LearningModuleResponse getModuleById(@PathVariable UUID id) {
        return learningModuleService.getLearningModuleById(id);
    }

    @Operation(summary = "Get learning modules",
            description = "Retrieves a paginated list of learning modules. If no tasks exist, an empty list is returned.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Learning modules retrieved successfully. Returns an empty list if no learning modules found."),
                    @ApiResponse(responseCode = "400", description = "Incorrect sorting property.")
            })
    @GetMapping
    public Page<LearningModuleResponse> getModulePage(Pageable pageable) {
        return learningModuleService.getLearningPage(pageable);
    }
}
