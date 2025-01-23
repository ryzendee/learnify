package com.ryzendee.repetitionservice.IT.handler;

import com.learnify.kafka.models.learningmodule.events.LearningModuleDeletedEvent;
import com.ryzendee.repetitionservice.IT.handler.common.AbstractEmbeddedKafkaHandlerTest;
import com.ryzendee.repetitionservice.handler.LearningModuleEventHandler;
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

@SpringBootTest(classes = {LearningModuleEventHandler.class})
public class LearningModuleEventHandlerEmbeddedKafkaIT extends AbstractEmbeddedKafkaHandlerTest {

    @SpyBean
    private LearningModuleEventHandler learningModuleEventHandler;

    @Value("${topic.learning-module.events.name}")
    private String learningModuleEventsTopic;

    @DisplayName("Should receive LearningModuleDeletedEvent and call service to delete all cards")
    @Test
    void handleCardCreatedEvent_shouldCallServiceToDeleteAllCards() throws Exception {
        var learningModuleDeletedEvent = new LearningModuleDeletedEvent(UUID.randomUUID());

        kafkaTemplate.send(learningModuleEventsTopic, learningModuleDeletedEvent).get();

        var argumentCaptorForEvent = ArgumentCaptor.forClass(LearningModuleDeletedEvent.class);
        verify(learningModuleEventHandler, timeout(10000))
                .handleLearningModuleDeletedEvent(argumentCaptorForEvent.capture());
        verify(cardRepetitionService)
                .deleteAllCardRepetitionsByLearningModuleId(learningModuleDeletedEvent.getLearningModuleId());
        assertThat(argumentCaptorForEvent.getValue())
                .isEqualTo(learningModuleDeletedEvent);
    }
}
