package com.pharmaflow.demo.Dto;

import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Entities.Sale;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record SaleItemsDto(
        UUID id,
        UUID productId,
        String productName,
        SaleDto saleDto,
        long quantity,
        BigDecimal priceAtSale
) {
}
