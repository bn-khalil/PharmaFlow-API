package com.pharmaflow.demo.Entities;

import com.pharmaflow.demo.Enums.Action;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audits")
public class Audit extends BaseEntity {
    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private long quantity;

    @Column(nullable = false)
    private String responsibleEmail;

    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(nullable = false)
    private long stockBefore;

    @Column(nullable = false)
    private long stockAfter;
}
