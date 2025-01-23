package com.ryzendee.repetitionservice.dto.repetition.response;

import java.time.LocalDateTime;

public record RepetitionUpdateResponse(
        int updatedRepetitionCount,
        double updatedEaseFactor,
        int updatedDayInterval,
        LocalDateTime nextRepetitionDate
) {
}