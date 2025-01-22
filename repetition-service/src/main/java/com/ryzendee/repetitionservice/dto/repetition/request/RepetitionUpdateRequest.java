package com.ryzendee.repetitionservice.dto.repetition.request;

import com.ryzendee.repetitionservice.enums.ReviewRating;

public record RepetitionUpdateRequest(
        int repetitionCount,
        double easeFactor,
        int dayInterval,
        ReviewRating reviewRating
) {
}
