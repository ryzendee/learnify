package com.ryzendee.moduleservice.mapper.learningmodule;

import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleUpdateRequest;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;



@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LearningModuleUpdateRequestMapper {
    LearningModuleEntity map(LearningModuleUpdateRequest request, @MappingTarget LearningModuleEntity entity);

}
