package com.ryzendee.moduleservice.dto.learningmodule.response;

import java.util.UUID;

public record LearningModuleResponse(
        UUID id,
        String name,
        String description
) {
}
