package com.pharmaflow.demo.Dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserNotificationDto (
        UUID id,
        String message,
        UUID productId,
        String productName,
        String status,
        boolean isRead,
        LocalDateTime createdAt
) {
}
