package com.ryzendee.imageservice.exception;

public class S3RepositoryException extends RuntimeException {

    public S3RepositoryException(String message) {
        super(message);
    }

    public S3RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
