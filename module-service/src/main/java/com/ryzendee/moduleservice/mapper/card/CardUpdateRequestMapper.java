package com.ryzendee.moduleservice.mapper.card;

import com.ryzendee.moduleservice.dto.card.request.CardUpdateRequest;
import com.ryzendee.moduleservice.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardUpdateRequestMapper {

    CardEntity map(CardUpdateRequest request, @MappingTarget CardEntity entity);
}
