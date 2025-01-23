package com.ryzendee.repetitionservice.mapper;

import com.learnify.kafka.models.card.events.CardCreatedEvent;
import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardRepetitionEntityMapper {

    CardRepetitionEntity map(CardCreatedEvent cardCreatedEvent);
}
