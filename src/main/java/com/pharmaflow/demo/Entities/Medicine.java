package com.pharmaflow.demo.Entities;

import jakarta.persistence.Column;
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
@Table(name = "medicines")
@PrimaryKeyJoinColumn(name = "product_id")
public class Medicine extends Product{

    @Column(nullable = false)
    private boolean prescription;

    private long dosageUnit;
}
