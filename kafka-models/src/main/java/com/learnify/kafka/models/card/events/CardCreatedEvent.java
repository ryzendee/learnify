package com.learnify.kafka.models.card.events;

import java.util.UUID;

public class CardCreatedEvent {

    private final UUID cardId;

    public CardCreatedEvent(UUID cardId) {
        this.cardId = cardId;
    }

    public UUID getCardId() {
        return cardId;
    }

    @Override
    public String toString() {
        return "CardCreatedEvent{" +
                "cardId=" + cardId +
                '}';
    }
}
