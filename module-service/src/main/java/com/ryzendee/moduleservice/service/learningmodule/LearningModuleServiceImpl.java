package com.ryzendee.moduleservice.service.learningmodule;

import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleCreateRequest;
import com.ryzendee.moduleservice.dto.learningmodule.request.LearningModuleUpdateRequest;
import com.ryzendee.moduleservice.dto.learningmodule.response.LearningModuleResponse;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import com.ryzendee.moduleservice.exception.LearningModuleNotFoundException;
import com.ryzendee.moduleservice.mapper.learningmodule.LearningModuleCreateRequestMapper;
import com.ryzendee.moduleservice.mapper.learningmodule.LearningModuleEntityMapper;
import com.ryzendee.moduleservice.repository.LearningModuleJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LearningModuleServiceImpl implements LearningModuleService {

    private final LearningModuleJpaRepository learningModuleJpaRepository;

    private final LearningModuleCreateRequestMapper moduleCreateRequestMapper;
    private final LearningModuleEntityMapper moduleEntityMapper;

    @Transactional
    @Override
    public LearningModuleResponse createLearningModule(LearningModuleCreateRequest request) {
        LearningModuleEntity entity = moduleCreateRequestMapper.map(request);
        learningModuleJpaRepository.save(entity);
        log.info("Learning module was saved, id: {}", entity.getId());

        return moduleEntityMapper.map(entity);
    }

    @Transactional
    @Override
    public LearningModuleResponse updateLearningModuleById(UUID id, LearningModuleUpdateRequest request) {
        LearningModuleEntity entity = getLearningModuleEntityById(id);
        entity.setName(request.name());
        entity.setDescription(request.description());
        learningModuleJpaRepository.save(entity);
        log.info("Learning module was updated, id: {}", entity.getId());

        return moduleEntityMapper.map(entity);
    }

    @Transactional
    @Override
    public void deleteLearningModuleById(UUID id) {
        LearningModuleEntity entity = getLearningModuleEntityById(id);
        learningModuleJpaRepository.delete(entity);
        log.info("Learning module was deleted, id: {}", id);
    }

    @Transactional(readOnly = true)
    @Override
    public LearningModuleResponse getLearningModuleById(UUID id) {
        LearningModuleEntity entity = getLearningModuleEntityById(id);
        return moduleEntityMapper.map(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<LearningModuleResponse> getLearningPage(Pageable pageable) {
        return learningModuleJpaRepository.findAll(pageable)
                .map(moduleEntityMapper::map);
    }

    private LearningModuleEntity getLearningModuleEntityById(UUID id) {
        return learningModuleJpaRepository.findById(id)
                .orElseThrow(() -> new LearningModuleNotFoundException("Learning module does not exists, id: " + id));
    }
}
