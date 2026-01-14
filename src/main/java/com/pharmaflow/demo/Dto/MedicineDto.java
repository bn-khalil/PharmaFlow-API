package com.pharmaflow.demo.Dto;

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
    private long dosageUnit;
}
