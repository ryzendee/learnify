package com.ryzendee.repetitionservice.contoller;

import com.ryzendee.repetitionservice.dto.repetition.request.CardRepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.response.CardRepetitionGetResponse;
import com.ryzendee.repetitionservice.service.CardRepetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/card-repetitions")
@RequiredArgsConstructor
public class CardRepetitionRestController {

    private CardRepetitionService cardRepetitionService;

    @GetMapping("/{learningModuleId}/repetitions")
    public List<CardRepetitionGetResponse> getCardRepetitionsForLearningModule(@PathVariable UUID learningModuleId) {
        return cardRepetitionService.getCardsForRepetitionByLearningModuleId(learningModuleId);
    }

    @PutMapping("/{cardId}")
    public void updateCardRepetitionByCardId(@PathVariable UUID cardId,
                                             @RequestBody CardRepetitionUpdateRequest request) {
        cardRepetitionService.updateCardRepetitionByCardId(cardId, request);
    }
}
