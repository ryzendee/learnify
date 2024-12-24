package com.ryzendee.moduleservice.testutils.builder.card;

import com.ryzendee.moduleservice.dto.card.request.CardUpdateRequest;
import com.ryzendee.moduleservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CardUpdateRequestBuilder implements TestBaseBuilder<CardUpdateRequest> {

    private String term = "cardUpdateRequestTerm";
    private String definition = "cardUpdateRequestDefinition";

    @Override
    public CardUpdateRequest build() {
        return new CardUpdateRequest(term, definition);
    }
}
