package com.ryzendee.repetitionservice.IT.handler.common;

import com.ryzendee.repetitionservice.service.CardRepetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@EmbeddedKafka(
        controlledShutdown = true,
        bootstrapServersProperty = "spring.kafka.consumer.bootstrap-servers"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Import(TestKafkaHandlerConfiguration.class)
public abstract class AbstractEmbeddedKafkaHandlerTest {

    @Autowired
    protected KafkaTemplate<String, Object> kafkaTemplate;

    @MockBean
    protected CardRepetitionService cardRepetitionService;
}
