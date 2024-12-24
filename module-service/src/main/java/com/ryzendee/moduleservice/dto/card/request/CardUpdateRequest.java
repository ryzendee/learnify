package com.ryzendee.moduleservice.dto.card.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CardUpdateRequest(
        @Size(max = 255, message = "Term must be not greater than {max} characters")
        @NotBlank(message = "Term must not be blank")
        String term,
        @Size(max = 1000, message = "Definition must be not greater than {max} characters")
        @NotBlank(message = "Definition must not be blank")
        String definition
) {
}
