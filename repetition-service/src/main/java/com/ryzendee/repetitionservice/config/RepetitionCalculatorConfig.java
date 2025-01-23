package com.ryzendee.repetitionservice.config;

import com.ryzendee.repetitionservice.enums.ReviewRating;
import com.ryzendee.repetitionservice.service.helpers.calculator.RepetitionCalculator;
import com.ryzendee.repetitionservice.service.helpers.calculator.RepetitionFactor;
import com.ryzendee.repetitionservice.service.helpers.calculator.SpacedRepetitionCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RepetitionCalculatorConfig {

    @Bean
    public RepetitionCalculator repetitionCalculator(Map<ReviewRating, RepetitionFactor> factorMap) {
        return new SpacedRepetitionCalculator(factorMap);
    }

    @Bean
    public Map<ReviewRating, RepetitionFactor> repetitionCalculatorFactorConfig() {
        Map<ReviewRating, RepetitionFactor> factorMap = new HashMap<>();

        factorMap.put(ReviewRating.AGAIN, new RepetitionFactor(0.2, -0.2));
        factorMap.put(ReviewRating.HARD, new RepetitionFactor(1.2, -0.15));
        factorMap.put(ReviewRating.NORMAL, new RepetitionFactor(1.0, 0.0));
        factorMap.put(ReviewRating.EASY, new RepetitionFactor(1.3, 0.15));

        return factorMap;
    }
}
