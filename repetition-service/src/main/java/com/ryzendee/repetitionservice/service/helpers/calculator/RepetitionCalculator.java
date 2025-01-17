package com.ryzendee.repetitionservice.service.helpers.calculator;

import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.enums.ReviewRating;

public interface RepetitionCalculator {

    CardRepetitionEntity updateCardRepetition(CardRepetitionEntity card, ReviewRating reviewRating);

}
