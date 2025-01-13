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

    private String answer = "cardUpdateRequestAnswer";
    private String question = "cardUpdateRequestQuestion";

    @Override
    public CardUpdateRequest build() {
        return new CardUpdateRequest(answer, question);
    }
}
