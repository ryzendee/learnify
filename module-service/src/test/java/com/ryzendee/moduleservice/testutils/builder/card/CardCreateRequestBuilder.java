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

    private String term = "cardCreateRequestTerm";
    private String definition = "cardCreateRequestDefinition";

    @Override
    public CardCreateRequest build() {
        return new CardCreateRequest(term, definition);
    }
}
