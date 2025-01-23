package com.learnify.kafka.models.card.events;

import java.util.Objects;
import java.util.UUID;

public class CardCreatedEvent {

    private UUID cardId;
    private UUID learningModuleId;

    public CardCreatedEvent() {
    }

    public CardCreatedEvent(UUID cardId, UUID learningModuleId) {
        this.cardId = cardId;
        this.learningModuleId = learningModuleId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public void setLearningModuleId(UUID learningModuleId) {
        this.learningModuleId = learningModuleId;
    }

    public UUID getCardId() {
        return cardId;
    }

    public UUID getLearningModuleId() {
        return learningModuleId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        CardCreatedEvent that = (CardCreatedEvent) object;

        return Objects.equals(cardId, that.cardId);
    }

    @Override
    public int hashCode() {
        return cardId != null ? cardId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CardCreatedEvent{" +
                "cardId=" + cardId +
                ", learningModuleId=" + learningModuleId +
                '}';
    }
}
