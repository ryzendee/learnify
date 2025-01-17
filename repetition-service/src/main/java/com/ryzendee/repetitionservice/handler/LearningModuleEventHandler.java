package com.ryzendee.repetitionservice.handler;

import com.learnify.kafka.models.learningmodule.events.LearningModuleDeletedEvent;
import com.ryzendee.repetitionservice.service.CardRepetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topic.learning-module.events.name}")
@Slf4j
@RequiredArgsConstructor
public class LearningModuleEventHandler {

    private final CardRepetitionService cardRepetitionService;

    @KafkaHandler
    public void handleLearningModuleDeletedEvent(@Payload LearningModuleDeletedEvent event) {
        log.info("Received learning module deleted event: {}", event);
        cardRepetitionService.deleteAllCardRepetitionsByLearningModuleId(event.getLearningModuleId());
    }
}
