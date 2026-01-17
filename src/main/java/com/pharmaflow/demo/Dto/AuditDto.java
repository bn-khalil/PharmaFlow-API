package com.pharmaflow.demo.Dto;

import com.pharmaflow.demo.Enums.Action;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AuditDto(
        UUID id,
        String productName,
        long quantity,
        String responsibleEmail,
        Action action,
        long stockBefore,
        long stockAfter,
        LocalDateTime createAt,
        LocalDateTime updatedAt
) {
}