package com.ryzendee.repetitionservice.testutils.builder.entity;

import com.ryzendee.repetitionservice.dto.repetition.response.RepetitionUpdateResponse;
import com.ryzendee.repetitionservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class RepetitionUpdateResponseBuilder implements TestBaseBuilder<RepetitionUpdateResponse> {

    private int updatedRepetitionCount = 2;
    private double updatedEaseFactor = 3;
    private int updatedDayInterval = 4;
    private LocalDateTime nextRepetitionDate = LocalDateTime.now();

    @Override
    public RepetitionUpdateResponse build() {
        return new RepetitionUpdateResponse(
                updatedRepetitionCount,
                updatedEaseFactor,
                updatedDayInterval,
                nextRepetitionDate
        );
    }
}
