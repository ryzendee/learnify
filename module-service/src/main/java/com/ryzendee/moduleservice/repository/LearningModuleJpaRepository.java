package com.ryzendee.moduleservice.repository;

import com.ryzendee.moduleservice.entity.LearningModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LearningModuleJpaRepository extends JpaRepository<LearningModuleEntity, UUID> {
}
