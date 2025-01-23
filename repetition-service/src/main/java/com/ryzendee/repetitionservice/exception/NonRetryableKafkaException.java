package com.ryzendee.repetitionservice.exception;

public class NonRetryableKafkaException extends RuntimeException {

    public NonRetryableKafkaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonRetryableKafkaException(Throwable cause) {
        super(cause);
    }
}
