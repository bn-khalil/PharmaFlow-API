package com.pharmaflow.demo.Dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CategoryDto(
        UUID id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
