package com.pharmaflow.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CategoryDto(
        UUID id,

        @Size(min = 3, max = 50)
        @NotBlank(message = "category must have name")
        String name,

        @NotBlank(message = "category must have description")
        String description,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) implements Serializable { }
