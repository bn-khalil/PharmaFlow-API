package com.pharmaflow.demo.Dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true )
public class MedicalSuppleDto extends ProductDto {
    @Min(value = 0, message = "size cannot be negative")
    private long size;
}
