package com.ryzendee.moduleservice.dto.card.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CardCreateRequest(

        @Size(max = 255, message = "Question must be not greater than {max} characters")
        @NotBlank(message = "Question must not be blank")
        String question,
        @Size(max = 1000, message = "Answer must be not greater than {max} characters")
        @NotBlank(message = "Answer must not be blank")
        String answer,
        @Size(max = 255, message = "Image object name must be not greater than {max} characters")
        String imageObjectName
) {
}
