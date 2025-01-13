package com.ryzendee.moduleservice.testutils.builder.card;

import com.ryzendee.moduleservice.dto.card.response.CardResponse;
import com.ryzendee.moduleservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.UUID;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CardResponseBuilder implements TestBaseBuilder<CardResponse> {

    private UUID id = UUID.randomUUID();
    private UUID learningModuleId = UUID.randomUUID();
    private String question = "cardResponseQuestion";
    private String answer = "cardResponseAnswer";

    @Override
    public CardResponse build() {
        return new CardResponse(id, learningModuleId, question, answer);
    }
}
