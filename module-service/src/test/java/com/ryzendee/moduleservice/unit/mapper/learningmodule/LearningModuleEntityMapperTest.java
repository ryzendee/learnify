package com.ryzendee.moduleservice.unit.mapper.learningmodule;

import com.ryzendee.moduleservice.mapper.learningmodule.LearningModuleEntityMapper;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleEntityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class LearningModuleEntityMapperTest {

    private LearningModuleEntityMapper learningModuleEntityMapper;

    @BeforeEach
    void setUp() {
        learningModuleEntityMapper = Mappers.getMapper(LearningModuleEntityMapper.class);
    }

    @Test
    void map() {
        var learningModuleEntity = LearningModuleEntityBuilder.builder()
                .withId(UUID.randomUUID())
                .build();

        var mappedLearningModuleResponse = learningModuleEntityMapper.map(learningModuleEntity);
        assertThat(mappedLearningModuleResponse.id()).isEqualTo(learningModuleEntity.getId());
        assertThat(mappedLearningModuleResponse.name()).isEqualTo(learningModuleEntity.getName());
        assertThat(mappedLearningModuleResponse.description()).isEqualTo(learningModuleEntity.getDescription());
    }
}
