package com.ryzendee.moduleservice.exception;

public class LearningModuleNotFoundException extends ResourceNotFoundException {
    public LearningModuleNotFoundException() {
    }

    public LearningModuleNotFoundException(String message) {
        super(message);
    }
}
