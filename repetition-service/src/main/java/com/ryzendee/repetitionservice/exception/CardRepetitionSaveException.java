package com.ryzendee.repetitionservice.exception;

public class CardRepetitionSaveException extends RuntimeException {

    public CardRepetitionSaveException(String message) {
        super(message);
    }

    public CardRepetitionSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
