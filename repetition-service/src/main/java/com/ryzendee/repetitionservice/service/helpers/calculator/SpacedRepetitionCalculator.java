package com.ryzendee.repetitionservice.service.helpers.calculator;

import com.ryzendee.repetitionservice.dto.repetition.request.RepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.response.RepetitionUpdateResponse;
import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SpacedRepetitionCalculator implements RepetitionCalculator {

    private static final double MIN_EASE_FACTOR = 1.3;

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
        int dayInterval = request.dayInterval();
        double easeFactor = request.easeFactor();
        int repetitionCount = request.repetitionCount();

        switch (request.reviewRating()) {
            case AGAIN -> {
                dayInterval = 1;
                easeFactor = Math.max(easeFactor - 0.2, MIN_EASE_FACTOR);
            }
            case HARD -> {
                dayInterval = (int) Math.round(dayInterval * 1.2);
                easeFactor = Math.max(easeFactor - 0.15, MIN_EASE_FACTOR);
            }
            case NORMAL -> dayInterval = (int) Math.round(dayInterval * easeFactor);
            case EASY -> {
                dayInterval = (int) Math.round(dayInterval * easeFactor * 1.3);
                easeFactor += 0.15;
            }
        }

        return new RepetitionUpdateResponse(repetitionCount + 1, easeFactor, dayInterval, LocalDateTime.now().plusDays(dayInterval));
    }
}
