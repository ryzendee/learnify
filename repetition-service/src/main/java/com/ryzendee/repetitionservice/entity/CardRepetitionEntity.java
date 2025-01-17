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

    private double easeFactor;

    private int repetitionCount;

    private int dayInterval;

    private LocalDateTime lastRepetitionDate;

    private LocalDateTime nextRepetitionDate;

}
