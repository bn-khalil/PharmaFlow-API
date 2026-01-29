package com.pharmaflow.demo.Dto;

import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Entities.Sale;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record SaleItemsDto(
        UUID id,

        @NotNull(message = "product is required")
        UUID productId,

        UUID saleId,

        @NotBlank(message = "product name is require")
        String productName,

        @Min(value = 1, message = "product number must be at least 1")
        long quantity,

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal priceAtSale
) { }
