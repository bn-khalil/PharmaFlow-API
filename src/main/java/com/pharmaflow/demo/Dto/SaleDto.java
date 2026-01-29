package com.pharmaflow.demo.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record SaleDto(
        UUID id,

        @NotNull(message = "Total amount cannot be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Total amount cannot be negative")
        BigDecimal totalAmount,

        @NotBlank(message = "Saler name is required")
        String salerName,

        @NotEmpty(message = "Sale must contain at least one item")
        @Valid
        List<SaleItemsDto> saleItemsDtos,

        @Min(value = 1, message = "Items number must be at least 1")
        long itemsNumber,

        LocalDateTime createAt
) { }
