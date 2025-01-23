package com.ryzendee.repetitionservice.dto.repetition.request;

import com.ryzendee.repetitionservice.enums.ReviewRating;
import jakarta.validation.constraints.NotNull;

public record CardRepetitionUpdateRequest(
        @NotNull(message = "Review rating must not be null")
        ReviewRating reviewRating
) {
}
