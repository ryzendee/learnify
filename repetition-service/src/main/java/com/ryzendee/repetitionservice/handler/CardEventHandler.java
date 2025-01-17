package com.ryzendee.repetitionservice.handler;

import com.learnify.kafka.models.card.events.CardCreatedEvent;
import com.learnify.kafka.models.card.events.CardDeletedEvent;
import com.ryzendee.repetitionservice.exception.CardRepetitionNotFoundException;
import com.ryzendee.repetitionservice.exception.CardRepetitionSaveException;
import com.ryzendee.repetitionservice.exception.NonRetryableKafkaException;
import com.ryzendee.repetitionservice.service.CardRepetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topic.card.events.name}")
@Slf4j
@RequiredArgsConstructor
public class CardEventHandler {

    private final CardRepetitionService cardRepetitionService;

    @KafkaHandler
    public void handleCardCreatedEvent(@Payload CardCreatedEvent event) {
        try {
            log.info("Received card created event: {}", event);
            cardRepetitionService.createCardRepetition(event);
        } catch (CardRepetitionSaveException ex) {
            throw new NonRetryableKafkaException(ex);
        }
    }

    @KafkaHandler
    public void handleCardDeletedEvent(@Payload CardDeletedEvent event) {
        try {
            log.info("Received card deleted event: {}", event);
            cardRepetitionService.deleteCardRepetitionByCardId(event.getCardId());
        } catch (CardRepetitionNotFoundException ex) {
            throw new NonRetryableKafkaException(ex);
        }
    }
}
