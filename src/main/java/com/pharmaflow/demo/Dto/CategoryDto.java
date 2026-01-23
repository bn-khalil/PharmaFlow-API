package com.pharmaflow.demo.Dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CategoryDto(
        UUID id,
        @Size(max = 50)
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
