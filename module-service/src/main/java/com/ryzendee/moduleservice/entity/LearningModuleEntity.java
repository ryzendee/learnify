package com.ryzendee.moduleservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "learning_modules")
@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LearningModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private String imageObjectName;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "learningModuleEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<CardEntity> cardEntityList = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void addCard(CardEntity cardEntity) {
        cardEntityList.add(cardEntity);
        cardEntity.setLearningModuleEntity(this);
    }
}
