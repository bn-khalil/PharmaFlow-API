package com.pharmaflow.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "medicines")
public class Medicine extends Product{

    @Column(nullable = false)
    private boolean Prescription;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "dosage_unit")
    private long dosageUnit;
}
