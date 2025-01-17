package com.learnify.kafka.models.card.events;

import java.util.UUID;

public class CardDeletedEvent {
    private final UUID cardId;

    public CardDeletedEvent(UUID cardId) {
        this.cardId = cardId;
    }

    public UUID getCardId() {
        return cardId;
    }

    @Override
    public String toString() {
        return "CardDeletedEvent{" +
                "cardId=" + cardId +
                '}';
    }
}
