package com.ryzendee.repetitionservice.contoller;

import com.ryzendee.repetitionservice.dto.repetition.request.CardRepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.response.CardRepetitionGetResponse;
import com.ryzendee.repetitionservice.service.CardRepetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/card-repetitions")
@RequiredArgsConstructor
public class CardRepetitionRestController {

    private final CardRepetitionService cardRepetitionService;

    @GetMapping("/due")
    public List<CardRepetitionGetResponse> getCardRepetitionsForLearningModule(@RequestParam UUID learningModuleId) {
        return cardRepetitionService.getCardsForRepetitionByLearningModuleId(learningModuleId);
    }

    @PutMapping("/{cardId}/review")
    public void updateCardRepetitionByCardId(@PathVariable UUID cardId,
                                             @Valid @RequestBody CardRepetitionUpdateRequest request) {
        cardRepetitionService.updateCardRepetitionByCardId(cardId, request);
    }
}
