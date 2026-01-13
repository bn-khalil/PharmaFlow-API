package com.pharmaflow.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_supplies")
@PrimaryKeyJoinColumn(name = "product_id")
public class MedicalSupple extends Product{
    private long size;
}
