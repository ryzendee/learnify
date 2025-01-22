package com.ryzendee.repetitionservice.service.helpers.calculator;

import com.ryzendee.repetitionservice.dto.repetition.request.RepetitionUpdateRequest;
import com.ryzendee.repetitionservice.dto.repetition.response.RepetitionUpdateResponse;
import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.enums.ReviewRating;

public interface RepetitionCalculator {

    RepetitionUpdateResponse calculate(RepetitionUpdateRequest request);
}
