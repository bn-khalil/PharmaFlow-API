package com.pharmaflow.demo.Dto;

import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MedicineDto extends ProductDto {
    private boolean prescription;

    @Min(value = 0, message = "dosageUnit cannot be negative")
    private long dosageUnit;
}
