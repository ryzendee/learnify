package com.ryzendee.repetitionservice.service.helpers.calculator;

import com.ryzendee.repetitionservice.dto.repetition.request.RepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.response.RepetitionUpdateResponse;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SpacedRepetitionCalculator implements RepetitionCalculator {

    private static final double MIN_EASE_FACTOR = 1.3;

    private final Map<ReviewRating, RepetitionFactor> repetitionFactorMap;

    /**
     * Updates the card repetition parameters based on the user's review rating.
     * The algorithm follows a spaced repetition approach, adjusting the interval
     * and ease factor dynamically.
     * <p>
     * Rating impact:
     * - AGAIN (0): User failed → Reset interval to 1 day, decrease ease factor.
     * - HARD (1): User struggled → Slightly increase interval, decrease ease factor.
     * - NORMAL (2): User answered correctly → Multiply interval by ease factor.
     * - EASY (3): User found it easy → Increase interval more aggressively, boost ease factor.
     * <p>
     * The ease factor controls how quickly intervals grow. It cannot drop below 1.3.
     * The next review date is calculated based on the updated interval.
     */
    @Override
    public RepetitionUpdateResponse calculate(RepetitionUpdateRequest request) {
        RepetitionFactor repetitionFactor = repetitionFactorMap.get(request.reviewRating());

        int updatedDayInterval = (int) Math.round(request.dayInterval() * repetitionFactor.intervalMultiplier());
        double updatedEaseFactor = Math.max(request.easeFactor() + repetitionFactor.easeFactor(), MIN_EASE_FACTOR);

        return new RepetitionUpdateResponse(
                request.repetitionCount() + 1,
                updatedEaseFactor,
                updatedDayInterval,
                LocalDateTime.now().plusDays(updatedDayInterval)
        );
    }
}
