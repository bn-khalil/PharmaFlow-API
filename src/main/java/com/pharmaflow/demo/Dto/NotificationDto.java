package com.pharmaflow.demo.Dto;

import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Enums.Notif;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record NotificationDto(
        UUID id,
        String message,
        Product product,
        Notif status,
        boolean isRead,
        LocalDateTime createdAt
) {
}
