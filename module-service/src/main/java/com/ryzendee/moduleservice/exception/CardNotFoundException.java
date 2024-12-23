package com.ryzendee.moduleservice.exception;

public class CardNotFoundException extends ResourceNotFoundException {
    public CardNotFoundException() {
    }

    public CardNotFoundException(String message) {
        super(message);
    }
}
