package com.ryzendee.moduleservice.dto.card.request;

import jakarta.validation.constraints.Size;

public record CardUpdateRequest(
        @Size(min = 1, max = 255, message = "Term must be between {min} and {max} characters")
        String term,
        @Size(min = 1, max = 1000, message = "Definition must be between {min} and {max} characters")
        String definition
) {
}
