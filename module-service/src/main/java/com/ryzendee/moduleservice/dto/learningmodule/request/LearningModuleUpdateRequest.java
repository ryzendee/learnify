package com.ryzendee.moduleservice.dto.learningmodule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LearningModuleUpdateRequest(

        @Size(min = 1, max = 255, message = "Name must be between {min} and {max} characters")
        @NotBlank(message = "Name must not be blank")
        String name,
        @Size(max = 1000, message = "Description must be not greater than {max} characters")
        String description
) {
}
