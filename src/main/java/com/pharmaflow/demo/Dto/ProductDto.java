package com.pharmaflow.demo.Dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pharmaflow.demo.Entities.Category;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "productType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MedicineDto.class, name = "MEDICINE"),
        @JsonSubTypes.Type(value = MedicalSuppleDto.class, name = "SUPPLY")
})
public abstract class ProductDto {
        protected UUID id;
        protected String name;
        protected String image;
        protected long quantity;
        protected LocalDateTime expiryDate;
        private boolean expiredStatus;
        private boolean nearExpiredStatus;
        protected BigDecimal price;
        protected String barcode;
        protected String category;
        protected LocalDateTime createdAt;
        protected LocalDateTime updatedAt;
}
