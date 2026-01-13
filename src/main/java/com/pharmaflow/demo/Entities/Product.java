package com.pharmaflow.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String image;

    private long quantity;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
