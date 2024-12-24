package com.ryzendee.moduleservice.controller;

import com.ryzendee.moduleservice.dto.card.request.CardCreateRequest;
import com.ryzendee.moduleservice.dto.card.request.CardUpdateRequest;
import com.ryzendee.moduleservice.dto.card.response.CardResponse;
import com.ryzendee.moduleservice.service.card.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/learning-modules/{learningModuleId}/cards")
@RequiredArgsConstructor
public class CardRestController {

    private final CardService cardService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CardResponse createCard(@Valid @RequestBody CardCreateRequest request,
                                   @PathVariable UUID learningModuleId) {
        return cardService.createCard(learningModuleId, request);
    }

    @PutMapping("/{id}")
    public CardResponse updateCardById(@Valid @RequestBody CardUpdateRequest request,
                                       @PathVariable UUID id) {
        return cardService.updateCardById(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCardById(@PathVariable UUID id) {
        cardService.deleteCardById(id);
    }

    @GetMapping("/{id}")
    public CardResponse getCardById(@PathVariable UUID id) {
        return cardService.getCardById(id);
    }

    @GetMapping
    public Page<CardResponse> getCardPageByLearningModuleId(@PathVariable UUID learningModuleId,
                                                            Pageable pageable) {
        return cardService.getCardPageByLearningModuleId(learningModuleId, pageable);
    }
}
