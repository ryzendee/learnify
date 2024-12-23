package com.ryzendee.moduleservice.mapper.learningmodule;

import com.ryzendee.moduleservice.dto.learningmodule.response.LearningModuleResponse;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LearningModuleEntityMapper {
    LearningModuleResponse map(LearningModuleEntity entity);
}
