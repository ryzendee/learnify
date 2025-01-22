package com.learnify.kafka.models.learningmodule.events;

import java.util.Objects;
import java.util.UUID;

public class LearningModuleDeletedEvent {

    private UUID learningModuleId;

    public LearningModuleDeletedEvent() {
    }

    public LearningModuleDeletedEvent(UUID learningModuleId) {
        this.learningModuleId = learningModuleId;
    }

    public UUID getLearningModuleId() {
        return learningModuleId;
    }

    public void setLearningModuleId(UUID learningModuleId) {
        this.learningModuleId = learningModuleId;
    }
    @Override
    public String toString() {
        return "LearningModuleDeletedEvent{" +
                "learningModuleId=" + learningModuleId +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        LearningModuleDeletedEvent that = (LearningModuleDeletedEvent) object;

        return Objects.equals(learningModuleId, that.learningModuleId);
    }

    @Override
    public int hashCode() {
        return learningModuleId != null ? learningModuleId.hashCode() : 0;
    }
}
