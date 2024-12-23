package com.ryzendee.moduleservice.dto.card.response;

import java.util.UUID;

public record CardResponse(
        UUID id,
        UUID learningModuleId,
        String term,
        String definition
) {
}
