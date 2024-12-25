package com.ryzendee.moduleservice.integration.service;

import com.ryzendee.moduleservice.TestcontainersConfiguration;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import com.ryzendee.moduleservice.exception.LearningModuleNotFoundException;
import com.ryzendee.moduleservice.mapper.learningmodule.LearningModuleCreateRequestMapper;
import com.ryzendee.moduleservice.mapper.learningmodule.LearningModuleEntityMapper;
import com.ryzendee.moduleservice.service.learningmodule.LearningModuleService;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleCreateRequestBuilder;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleEntityBuilder;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleResponseBuilder;
import com.ryzendee.moduleservice.testutils.builder.learningmodule.LearningModuleUpdateRequestBuilder;
import com.ryzendee.moduleservice.testutils.config.TestConfig;
import com.ryzendee.moduleservice.testutils.facade.TestDatabaseFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@Import({TestConfig.class, TestcontainersConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LearningModuleServiceDbIT {

    @Autowired
    private LearningModuleService learningModuleService;
    @Autowired
    private TestDatabaseFacade testDatabaseFacade;

    @MockBean
    private LearningModuleEntityMapper entityMapper;
    @MockBean
    private LearningModuleCreateRequestMapper createRequestMapper;

    private LearningModuleEntity preparedEntity;

    @BeforeEach
    void setUpDatabaseData() {
        cleanDatabaseData();
        preparedEntity = insertLearningModule();
    }

    @DisplayName("Should save module in database")
    @Test
    void createLearningModule_shouldSaveModule() {
        // Given
        var createRequest = LearningModuleCreateRequestBuilder.builder().build();
        var entityFromCreateRequest = LearningModuleEntityBuilder.builder().build();
        when(createRequestMapper.map(createRequest)).thenReturn(entityFromCreateRequest);

        // Act
        learningModuleService.createLearningModule(createRequest);

        // Assert
        var savedEntity = testDatabaseFacade.find(entityFromCreateRequest.getId(), LearningModuleEntity.class);
        verify(createRequestMapper).map(createRequest);
        assertThat(savedEntity).isEqualTo(entityFromCreateRequest);
        assertThat(savedEntity.getCreatedAt()).isNotNull();
    }

    @DisplayName("Should update module with given id in database")
    @Test
    void updateEntity_withEntityInDb_shouldUpdateEntity() {
        // Given
        var updateRequest = LearningModuleUpdateRequestBuilder.builder().build();

        // Act
        learningModuleService.updateLearningModuleById(preparedEntity.getId(), updateRequest);

        // Assert
        var updatedEntity = testDatabaseFacade.find(preparedEntity.getId(), LearningModuleEntity.class);
        assertThat(updatedEntity.getName()).isEqualTo(updateRequest.name());
        assertThat(updatedEntity.getDescription()).isEqualTo(updateRequest.description());
        assertThat(updatedEntity.getUpdatedAt()).isNotNull();
    }

    @DisplayName("Should throw LearningModuleNotFoundEx and not update module")
    @Test
    void updateEntity_withoutEntityInDb_shouldThrowLearningModuleNotFoundEx() {
        var updateRequest = LearningModuleUpdateRequestBuilder.builder().build();

        assertThatThrownBy(
                () -> learningModuleService.updateLearningModuleById(UUID.randomUUID(), updateRequest))
                .isInstanceOf(LearningModuleNotFoundException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should return module with given id")
    @Test
    void getLearningModuleById_withModuleInDb_shouldReturnModule() {
        var expectedLearningModuleResponse = LearningModuleResponseBuilder.builder().build();
        when(entityMapper.map(preparedEntity)).thenReturn(expectedLearningModuleResponse);

        var actualResponse = learningModuleService.getLearningModuleById(preparedEntity.getId());
        verify(entityMapper).map(preparedEntity);
        assertThat(actualResponse).isEqualTo(expectedLearningModuleResponse);
    }

    @DisplayName("Should throw LearningModuleNotFoundEx and not return module")
    @Test
    void getLearningModuleById_withoutModuleInDb_shouldThrowLearningModuleNotFoundEx() {
        assertThatThrownBy(() -> learningModuleService.getLearningModuleById(UUID.randomUUID()))
                .isInstanceOf(LearningModuleNotFoundException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should delete module with given id")
    @Test
    void deleteLearningModuleById_withModuleInDb_shouldDeleteModule() {
        learningModuleService.deleteLearningModuleById(preparedEntity.getId());

        var entity = testDatabaseFacade.find(preparedEntity.getId(), LearningModuleEntity.class);
        assertThat(entity).isNull();
    }

    @DisplayName("Should throw LearningModuleNotFoundEx and not delete module")
    @Test
    void deleteLearningModuleById_withoutModuleInDb_shouldThrowLearningModuleNotFoundException() {
        assertThatThrownBy(
                () -> learningModuleService.deleteLearningModuleById(UUID.randomUUID()))
                .isInstanceOf(LearningModuleNotFoundException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should return learning module page")
    @Test
    void getLearningPage_withModuleInDb_shouldReturnPage() {
        // Given
        var pageable = PageRequest.of(0, 1);
        var expectedLearningModuleResponse = LearningModuleResponseBuilder.builder().build();

        when(entityMapper.map(preparedEntity)).thenReturn(expectedLearningModuleResponse);

        // Act
        var learningModuleResponsePage = learningModuleService.getLearningPage(pageable);

        // Assert
        assertThat(learningModuleResponsePage).containsOnly(expectedLearningModuleResponse);
    }

    @DisplayName("Should throw PropertyReferenceEx due to incorrect sorting property")
    @Test
    void getLearningPage_invalidSortingProperty_shouldThrowPropertyReferenceEx() {
        var sort = Sort.by("dummy");
        var pageable = PageRequest.of(0, 1, sort);

        assertThatThrownBy(() -> learningModuleService.getLearningPage(pageable))
                .isInstanceOf(PropertyReferenceException.class)
                .message().isNotBlank();
    }

    private LearningModuleEntity insertLearningModule() {
        return testDatabaseFacade.save(LearningModuleEntityBuilder.builder());
    }

    private void cleanDatabaseData() {
        testDatabaseFacade.cleanDatabase();
    }
}
