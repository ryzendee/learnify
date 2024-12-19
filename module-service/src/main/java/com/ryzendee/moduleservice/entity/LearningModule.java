package com.ryzendee.moduleservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "learning_modules")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearningModule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;

    @Builder.Default
    @OneToMany(
            mappedBy = "learningModule",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Card> cardList = new ArrayList<>();

    public void addCard(Card card) {
        cardList.add(card);
        card.setLearningModule(this);
    }
}
