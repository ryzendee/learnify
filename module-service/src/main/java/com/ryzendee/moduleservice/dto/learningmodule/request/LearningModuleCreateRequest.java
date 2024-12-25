package com.ryzendee.moduleservice.dto.learningmodule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LearningModuleCreateRequest(

        @Size(max = 255, message = "Name must be must be not greater than and {max} characters")
        @NotBlank(message = "Name must not be blank")
        String name,
        @Size(max = 1000, message = "Description must be not greater than {max} characters")
        String description
) {
}
