package com.pharmaflow.demo.Dto;

import com.pharmaflow.demo.Entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductDto {
        private UUID id;
        private String name;
        private String image;
        private long quantity;
        private LocalDateTime expiryDate;
        private BigDecimal price;
        private String barcode;
        private String category;
}
