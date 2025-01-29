package com.ryzendee.userpreferenceservice.mapper;

import com.ryzendee.userpreferenceservice.document.UserPreferenceDocument;
import com.ryzendee.userpreferenceservice.dto.preference.response.UserPreferenceGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserPreferenceGetResponseMapper {

    UserPreferenceGetResponse map(UserPreferenceDocument document);
}
