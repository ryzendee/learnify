package com.ryzendee.moduleservice.unit.mapper.learningmodule;

import com.ryzendee.moduleservice.mapper.learningmodule.LearningModuleCreateRequestMapper;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleCreateRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class LearningModuleCreateRequestMapperTest {

    private LearningModuleCreateRequestMapper learningModuleCreateRequestMapper;

    @BeforeEach
    void setUp() {
        learningModuleCreateRequestMapper = Mappers.getMapper(LearningModuleCreateRequestMapper.class);
    }

    @Test
    void map() {
        var learningModuleCreateRequest = LearningModuleCreateRequestBuilder.builder().build();

        var mappedEntity = learningModuleCreateRequestMapper.map(learningModuleCreateRequest);
        assertThat(mappedEntity.getName()).isEqualTo(learningModuleCreateRequest.name());
        assertThat(mappedEntity.getDescription()).isEqualTo(learningModuleCreateRequest.description());
    }
}
