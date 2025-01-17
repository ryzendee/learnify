package com.ryzendee.repetitionservice.repository;

import com.ryzendee.repetitionservice.entity.CardRepetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepetitionJpaRepository extends JpaRepository<CardRepetitionEntity, UUID> {

    @Query("""
    SELECT cre
    FROM CardRepetitionEntity cre
    WHERE cre.learningModuleId =: learningModuleId
        AND cre.nextRepetitionDate <= CURRENT_TIMESTAMP
    """)
    List<CardRepetitionEntity> findActualRepetitionsByLearningModuleId(@Param("learningModuleId") UUID learningModuleId);
    Optional<CardRepetitionEntity> findByCardId(UUID cardId);
    void deleteAllByLearningModuleId(UUID learningModuleId);
}
