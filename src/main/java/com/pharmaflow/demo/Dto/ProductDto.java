package com.pharmaflow.demo.Dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pharmaflow.demo.validation.OnCreate;
import jakarta.validation.constraints.*;
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

        @NotBlank(groups = OnCreate.class,message = "Product name is mandatory")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        protected String name;

        protected String image;

        protected String imageUrl;

        @Min(value = 0, message = "Quantity cannot be negative")
        protected long quantity;

        @Future(message = "Expiry date must be in the future")
        protected LocalDateTime expiryDate;

        private boolean expiredStatus;
        private boolean nearExpiredStatus;
        private boolean active;

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        @Digits(integer = 7, fraction = 2, message = "Price format invalid (up to 7 digits and 2 decimals)")
        protected BigDecimal price;

        @NotBlank(groups = OnCreate.class,message = "Barcode is required for stock management")
        protected String barcode;

        @NotBlank(groups = OnCreate .class,message = "Category is mandatory")
        protected String category;

        protected LocalDateTime createdAt;
        protected LocalDateTime updatedAt;
}
