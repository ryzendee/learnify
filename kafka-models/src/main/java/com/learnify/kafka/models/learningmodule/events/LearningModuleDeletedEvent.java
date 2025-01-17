package com.learnify.kafka.models.learningmodule.events;

import java.util.UUID;

public class LearningModuleDeletedEvent {

    private final UUID learningModuleId;

    public LearningModuleDeletedEvent(UUID learningModuleId) {
        this.learningModuleId = learningModuleId;
    }

    public UUID getLearningModuleId() {
        return learningModuleId;
    }

    @Override
    public String toString() {
        return "LearningModuleDeletedEvent{" +
                "learningModuleId=" + learningModuleId +
                '}';
    }
}
