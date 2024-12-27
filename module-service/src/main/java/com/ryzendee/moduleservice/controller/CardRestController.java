package com.ryzendee.moduleservice.controller;

import com.ryzendee.moduleservice.dto.card.request.CardCreateRequest;
import com.ryzendee.moduleservice.dto.card.request.CardUpdateRequest;
import com.ryzendee.moduleservice.dto.card.response.CardResponse;
import com.ryzendee.moduleservice.service.card.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SecurityRequirement(name = "keycloak")
@RestController
@RequestMapping("/api/v1/learning-modules/{learningModuleId}/cards")
@RequiredArgsConstructor
public class CardRestController {

    private final CardService cardService;

    @Operation(summary = "Create a new card",
            description = "Creates a new card based on the provided request data.",
            parameters = {
                    @Parameter(
                            name = "learningModuleId",
                            description = "The ID of the learning module the card belongs to.",
                            required = true,
                            in = ParameterIn.PATH
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Card created successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid request data."),
                    @ApiResponse(responseCode = "404", description = "Learning module with given id not found.")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CardResponse createCard(@Valid @RequestBody CardCreateRequest request,
                                   @PathVariable UUID learningModuleId) {
        return cardService.createCard(learningModuleId, request);
    }

    @Operation(summary = "Update a card by ID",
            description = "Updates an existing card identified by its unique ID using the provided request data.",
            parameters = {
                    @Parameter(name = "id",
                            description = "The ID of the card to update.",
                            required = true,
                            in = ParameterIn.PATH),
                    @Parameter(
                            name = "learningModuleId",
                            description = "The ID of the learning module the card belongs to.",
                            required = true,
                            in = ParameterIn.PATH
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Card updated successfully."),
                    @ApiResponse(responseCode = "404", description = "Card not found for the given ID."),
                    @ApiResponse(responseCode = "400", description = "Invalid request data.")
            })
    @PutMapping("/{id}")
    public CardResponse updateCardById(@Valid @RequestBody CardUpdateRequest request,
                                       @PathVariable UUID id) {
        return cardService.updateCardById(id, request);
    }

    @Operation(summary = "Delete a card by ID",
            description = "Deletes a specific card identified by its unique ID.",
            parameters = {
                    @Parameter(name = "id",
                            description = "The ID of the card to update.",
                            required = true,
                            in = ParameterIn.PATH),
                    @Parameter(
                            name = "learningModuleId",
                            description = "The ID of the learning module the card belongs to.",
                            required = true,
                            in = ParameterIn.PATH
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Card deleted successfully."),
                    @ApiResponse(responseCode = "404", description = "Card not found for the given ID.")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCardById(@PathVariable UUID id) {
        cardService.deleteCardById(id);
    }

    @Operation(summary = "Get a card by ID",
            description = "Retrieves a specific card based on its unique identifier.",
            parameters = {
                    @Parameter(name = "id",
                            description = "The ID of the card to update.",
                            required = true,
                            in = ParameterIn.PATH),
                    @Parameter(
                            name = "learningModuleId",
                            description = "The ID of the learning module the card belongs to.",
                            required = true,
                            in = ParameterIn.PATH
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Card retrieved successfully."),
                    @ApiResponse(responseCode = "404", description = "Card not found for the given ID.")
            })
    @GetMapping("/{id}")
    public CardResponse getCardById(@PathVariable UUID id) {
        return cardService.getCardById(id);
    }

    @Operation(summary = "Get cards",
            description = "Retrieves a paginated list of cards. If no tasks exist, an empty list is returned.",
            parameters = {
                    @Parameter(
                            name = "learningModuleId",
                            description = "The ID of the learning module the card belongs to.",
                            required = true,
                            in = ParameterIn.PATH
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cards retrieved successfully. Returns an empty list if no cards found."),
                    @ApiResponse(responseCode = "400", description = "Incorrect sorting property.")
            })
    @GetMapping
    public Page<CardResponse> getCardPageByLearningModuleId(@PathVariable UUID learningModuleId,
                                                            Pageable pageable) {
        return cardService.getCardPageByLearningModuleId(learningModuleId, pageable);
    }
}
