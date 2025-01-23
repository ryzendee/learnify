package com.ryzendee.repetitionservice.testutils.builder.entity;

import com.ryzendee.repetitionservice.dto.repetition.request.RepetitionUpdateRequest;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import com.ryzendee.repetitionservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class RepetitionUpdateRequestBuilder implements TestBaseBuilder<RepetitionUpdateRequest> {

    private int repetitionCount = 0;
    private double easeFactor = 2.5;
    private int dayInterval = 1;
    private ReviewRating reviewRating = ReviewRating.EASY;

    @Override
    public RepetitionUpdateRequest build() {
        return new RepetitionUpdateRequest(
                repetitionCount,
                easeFactor,
                dayInterval,
                reviewRating
        );
    }
}
