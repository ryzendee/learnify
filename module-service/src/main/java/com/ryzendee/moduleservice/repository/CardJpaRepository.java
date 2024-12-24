package com.ryzendee.moduleservice.repository;

import com.ryzendee.moduleservice.entity.CardEntity;
import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CardJpaRepository extends JpaRepository<CardEntity, UUID> {

    Page<CardEntity> findAllByLearningModuleEntity(LearningModuleEntity entity, Pageable pageable);

    @Query("""
    SELECT c
    FROM CardEntity c
        JOIN FETCH LearningModuleEntity
    WHERE c.id = :id
    """)
    Optional<CardEntity> findByIdWithLearningModule(UUID id);
}
