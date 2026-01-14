package com.pharmaflow.demo.Dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true )
public class MedicalSuppleDto extends ProductDto {
    private long size;
}
