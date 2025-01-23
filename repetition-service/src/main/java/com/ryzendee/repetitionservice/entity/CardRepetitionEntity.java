package com.ryzendee.repetitionservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "card_repetitions")
@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CardRepetitionEntity {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    private UUID cardId;

    private UUID learningModuleId;

    @Builder.Default
    private double easeFactor = 2.5;

    @Builder.Default
    private int repetitionCount = 0;

    @Builder.Default
    private int dayInterval = 1;

    @Builder.Default
    private LocalDateTime lastRepetitionDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime nextRepetitionDate = LocalDateTime.now();

}
