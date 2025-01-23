package com.ryzendee.repetitionservice.unit.service.helpers;

import com.ryzendee.repetitionservice.dto.repetition.request.RepetitionUpdateRequest;
import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import com.ryzendee.repetitionservice.service.helpers.calculator.RepetitionCalculator;
import com.ryzendee.repetitionservice.service.helpers.calculator.RepetitionFactor;
import com.ryzendee.repetitionservice.service.helpers.calculator.SpacedRepetitionCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestRepetitionCalculatorConfig.class)
@ExtendWith(SpringExtension.class)
public class SpacedRepetitionCalculatorTest {

    @Autowired
    private RepetitionCalculator repetitionCalculator;

    @Autowired
    private Map<ReviewRating, RepetitionFactor> factorMap;

    @DisplayName("Repetition rating 'AGAIN' resets interval and decreases ease factor")
    @Test
    void calculate_whenRatingIsAgain_shouldResetIntervalAndDecreaseEaseFactor() {
        var repetitionUpdateRequest = new RepetitionUpdateRequest(5, 2.5, 3, ReviewRating.AGAIN);
        var response = repetitionCalculator.calculate(repetitionUpdateRequest);

        assertThat(response.updatedDayInterval()).isEqualTo(1);
        assertThat(response.updatedEaseFactor()).isGreaterThanOrEqualTo(1.3);
        assertThat(response.updatedRepetitionCount()).isEqualTo(repetitionUpdateRequest.repetitionCount() + 1);
        assertThat(response.nextRepetitionDate()).isAfter(LocalDateTime.now());
    }

    @DisplayName("Repetition rating 'HARD' increases interval and decreases ease factor")
    @Test
    void calculate_whenRatingIsHard_shouldIncreaseIntervalAndDecreaseEaseFactor() {
        var request = new RepetitionUpdateRequest(5, 2.5, 3, ReviewRating.HARD);
        var factor = factorMap.get(ReviewRating.HARD);

        var expectedDayInterval = (int) Math.round(request.dayInterval() * factor.intervalMultiplier());
        var expectedEaseFactor = Math.max(request.easeFactor() + factor.easeFactor(), 1.3);
        var response = repetitionCalculator.calculate(request);

        assertThat(response.updatedDayInterval()).isEqualTo(expectedDayInterval);
        assertThat(response.updatedEaseFactor()).isEqualTo(expectedEaseFactor);
        assertThat(response.updatedRepetitionCount()).isEqualTo(request.repetitionCount() + 1);
        assertThat(response.nextRepetitionDate()).isAfter(LocalDateTime.now());
    }

    @DisplayName("Repetition rating 'NORMAL' multiplies interval by ease factor")
    @Test
    void calculate_whenRatingIsNormal_shouldMultiplyIntervalByEaseFactor() {
        var request = new RepetitionUpdateRequest(5, 2.5, 3, ReviewRating.NORMAL);
        var factor = factorMap.get(ReviewRating.NORMAL);

        var expectedDayInterval = (int) Math.round(request.dayInterval() * factor.intervalMultiplier());
        var expectedEaseFactor = request.easeFactor() + factor.easeFactor();
        var response = repetitionCalculator.calculate(request);

        assertThat(response.updatedDayInterval()).isEqualTo(expectedDayInterval);
        assertThat(response.updatedEaseFactor()).isEqualTo(expectedEaseFactor);
        assertThat(response.updatedRepetitionCount()).isEqualTo(request.repetitionCount() + 1);
        assertThat(response.nextRepetitionDate()).isAfter(LocalDateTime.now());
    }

    @DisplayName("Repetition rating 'EASY' increases interval and boosts ease factor")
    @Test
    void calculate_whenRatingIsEasy_shouldIncreaseIntervalAndBoostEaseFactor() {
        var request = new RepetitionUpdateRequest(5, 2.5, 3, ReviewRating.EASY);
        var factor = factorMap.get(ReviewRating.EASY);

        var expectedDayInterval = (int) Math.round(request.dayInterval() * factor.intervalMultiplier());
        var expectedEaseFactor = request.easeFactor() + factor.easeFactor();
        var response = repetitionCalculator.calculate(request);

        assertThat(response.updatedDayInterval()).isEqualTo(expectedDayInterval);
        assertThat(response.updatedEaseFactor()).isEqualTo(expectedEaseFactor);
        assertThat(response.updatedRepetitionCount()).isEqualTo(request.repetitionCount() + 1);
        assertThat(response.nextRepetitionDate()).isAfter(LocalDateTime.now());
    }
}
