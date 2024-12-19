package com.ryzendee.moduleservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "cards")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String term;
    private String definition;

    @ManyToOne(fetch = FetchType.LAZY)
    private LearningModuleEntity learningModuleEntity;
}
