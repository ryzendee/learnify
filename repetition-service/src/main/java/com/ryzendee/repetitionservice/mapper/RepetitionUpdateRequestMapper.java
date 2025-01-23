package com.ryzendee.repetitionservice.mapper;

import com.ryzendee.repetitionservice.dto.repetition.request.RepetitionUpdateRequest;
import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RepetitionUpdateRequestMapper {

    RepetitionUpdateRequest map(CardRepetitionEntity entity, ReviewRating reviewRating);
}
