package com.pharmaflow.demo.Dto;

import com.pharmaflow.demo.Entities.User;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record SaleDto(
        UUID id,
        BigDecimal totalAmount,
        String salerName,
        List<SaleItemsDto> saleItemsDtos,
        long itemsNumber,
        LocalDateTime createAt) {
}
