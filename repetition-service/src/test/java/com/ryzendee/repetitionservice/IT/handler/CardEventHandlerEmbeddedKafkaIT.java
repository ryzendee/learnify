package com.ryzendee.repetitionservice.IT.handler;

import com.learnify.kafka.models.card.events.CardCreatedEvent;
import com.learnify.kafka.models.card.events.CardDeletedEvent;
import com.ryzendee.repetitionservice.IT.handler.common.AbstractEmbeddedKafkaHandlerTest;
import com.ryzendee.repetitionservice.handler.CardEventHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {CardEventHandler.class})
public class CardEventHandlerEmbeddedKafkaIT extends AbstractEmbeddedKafkaHandlerTest {

    @SpyBean
    private CardEventHandler cardEventHandler;

    @Value("${topic.card.events.name}")
    private String cardEventsTopic;

    @DisplayName("Should receive CardCreatedEvent and call service to create card")
    @Test
    void handleCardCreatedEvent_shouldCallServiceToCreateCard() throws Exception {
        var cardCreatedEvent = new CardCreatedEvent(UUID.randomUUID(), UUID.randomUUID());

        kafkaTemplate.send(cardEventsTopic, cardCreatedEvent).get();

        var argumentCaptorForEvent = ArgumentCaptor.forClass(CardCreatedEvent.class);
        verify(cardEventHandler, timeout(10000)).handleCardCreatedEvent(argumentCaptorForEvent.capture());
        verify(cardRepetitionService).createCardRepetition(cardCreatedEvent);
        assertThat(argumentCaptorForEvent.getValue()).isEqualTo(cardCreatedEvent);
    }

    @DisplayName("Should receive CardDeletedEvent and call service to delete card")
    @Test
    void handleCardDeletedEvent_shouldCallServiceToDeleteCard() throws Exception {
        var cardDeletedEvent = new CardDeletedEvent(UUID.randomUUID());

        kafkaTemplate.send(cardEventsTopic, cardDeletedEvent).get();

        var argumentCaptorForEvent = ArgumentCaptor.forClass(CardDeletedEvent.class);
        verify(cardEventHandler, timeout(10000)).handleCardDeletedEvent(argumentCaptorForEvent.capture());
        verify(cardRepetitionService).deleteCardRepetitionByCardId(cardDeletedEvent.getCardId());
        assertThat(argumentCaptorForEvent.getValue()).isEqualTo(cardDeletedEvent);
    }

}
