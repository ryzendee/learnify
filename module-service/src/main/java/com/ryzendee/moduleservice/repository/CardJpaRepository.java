package com.ryzendee.moduleservice.repository;

import com.ryzendee.moduleservice.entity.CardEntity;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardJpaRepository extends JpaRepository<CardEntity, UUID> {

    Page<CardEntity> findAllByLearningModuleEntity(LearningModuleEntity entity, Pageable pageable);
}
