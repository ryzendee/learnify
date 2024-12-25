package com.ryzendee.moduleservice.dto.exception;

public record ValidationFieldErrorResponse(String field, String message) {
}
