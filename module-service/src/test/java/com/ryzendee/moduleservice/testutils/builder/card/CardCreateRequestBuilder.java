package com.ryzendee.moduleservice.testutils.builder.card;

import com.ryzendee.moduleservice.dto.card.request.CardCreateRequest;
import com.ryzendee.moduleservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor

public class CardCreateRequestBuilder implements TestBaseBuilder<CardCreateRequest> {

    private String question = "cardCreateRequestQuestion";
    private String answer = "cardCreateRequestAnswer";

    @Override
    public CardCreateRequest build() {
        return new CardCreateRequest(question, answer);
    }
}
