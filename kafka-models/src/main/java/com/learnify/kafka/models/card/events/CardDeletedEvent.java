package com.learnify.kafka.models.card.events;

import java.util.Objects;
import java.util.UUID;

public class CardDeletedEvent {
    private UUID cardId;

    public CardDeletedEvent() {
    }

    public CardDeletedEvent(UUID cardId) {
        this.cardId = cardId;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    @Override
    public String toString() {
        return "CardDeletedEvent{" +
                "cardId=" + cardId +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        CardDeletedEvent that = (CardDeletedEvent) object;

        return Objects.equals(cardId, that.cardId);
    }

    @Override
    public int hashCode() {
        return cardId != null ? cardId.hashCode() : 0;
    }
}
