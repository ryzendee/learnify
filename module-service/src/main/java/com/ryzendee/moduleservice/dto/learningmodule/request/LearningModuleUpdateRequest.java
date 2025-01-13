package com.ryzendee.moduleservice.dto.learningmodule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LearningModuleUpdateRequest(

        @Size(max = 255, message = "Name must be not greater than {max} characters")
        @NotBlank(message = "Name must not be blank")
        String name,
        @Size(max = 1000, message = "Description must be not greater than {max} characters")
        String description,
        @Size(max = 255, message = "Image object name must be not greater than {max} characters")
        String imageObjectName
) {
}
