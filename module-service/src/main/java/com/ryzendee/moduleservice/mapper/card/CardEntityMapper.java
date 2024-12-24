package com.ryzendee.moduleservice.mapper.card;

import com.ryzendee.moduleservice.dto.card.response.CardResponse;
import com.ryzendee.moduleservice.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardEntityMapper {

    CardResponse map(CardEntity entity);
}
