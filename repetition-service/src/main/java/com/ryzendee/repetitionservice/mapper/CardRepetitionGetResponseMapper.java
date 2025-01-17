package com.ryzendee.repetitionservice.mapper;

import com.ryzendee.repetitionservice.dto.repetition.response.CardRepetitionGetResponse;
import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardRepetitionGetResponseMapper {

    CardRepetitionGetResponse map(CardRepetitionEntity entity);
}
