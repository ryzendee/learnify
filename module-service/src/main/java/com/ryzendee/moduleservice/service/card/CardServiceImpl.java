package com.ryzendee.moduleservice.service.card;

import com.ryzendee.moduleservice.dto.card.request.CardCreateRequest;
import com.ryzendee.moduleservice.dto.card.request.CardUpdateRequest;
import com.ryzendee.moduleservice.dto.card.response.CardResponse;
import com.ryzendee.moduleservice.entity.CardEntity;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import com.ryzendee.moduleservice.exception.CardNotFoundException;
import com.ryzendee.moduleservice.exception.LearningModuleNotFoundException;
import com.ryzendee.moduleservice.mapper.card.CardCreateRequestMapper;
import com.ryzendee.moduleservice.mapper.card.CardEntityMapper;
import com.ryzendee.moduleservice.repository.CardJpaRepository;
import com.ryzendee.moduleservice.repository.LearningModuleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardJpaRepository cardJpaRepository;
    private final LearningModuleJpaRepository learningModuleJpaRepository;

    private final CardCreateRequestMapper cardCreateRequestMapper;
    private final CardEntityMapper cardEntityMapper;

    @Transactional
    @Override
    public CardResponse createCard(UUID learningModuleId, CardCreateRequest request) {
        LearningModuleEntity learningModuleEntity = learningModuleJpaRepository.findById(learningModuleId)
                .orElseThrow(() -> new LearningModuleNotFoundException("Learning module with given id does not exists. Id: " + learningModuleId));

        CardEntity createdEntity = cardCreateRequestMapper.map(request);
        learningModuleEntity.addCard(createdEntity);
        cardJpaRepository.save(createdEntity);

        return cardEntityMapper.map(createdEntity);
    }

    @Transactional
    @Override
    public CardResponse updateCardById(UUID id, CardUpdateRequest request) {
        CardEntity entityToUpdate = getCardWithLearningModuleById(id);
        entityToUpdate.setTerm(request.term());
        entityToUpdate.setDefinition(request.definition());
        cardJpaRepository.save(entityToUpdate);

        return cardEntityMapper.map(entityToUpdate);
    }

    @Transactional
    @Override
    public void deleteCardById(UUID id) {
        CardEntity entityToDelete = getCardEntityById(id);
        cardJpaRepository.delete(entityToDelete);
    }

    @Transactional(readOnly = true)
    @Override
    public CardResponse getCardById(UUID id) {
        CardEntity entityToReturn = getCardWithLearningModuleById(id);
        return cardEntityMapper.map(entityToReturn);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CardResponse> getCardPageByLearningModuleId(UUID learningModuleId, Pageable pageable) {
        LearningModuleEntity learningModuleEntity = LearningModuleEntity.builder()
                .id(learningModuleId)
                .build();

        return cardJpaRepository.findAllByLearningModuleEntity(learningModuleEntity, pageable)
                .map(cardEntityMapper::map);
    }

    private CardEntity getCardEntityById(UUID id) {
        return cardJpaRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card with given id does not exists. Id : " + id));
    }

    private CardEntity getCardWithLearningModuleById(UUID id) {
        return cardJpaRepository.findByIdWithLearningModule(id)
                .orElseThrow(() -> new CardNotFoundException("Card with given id does not exists. Id : " + id));
    }
}
