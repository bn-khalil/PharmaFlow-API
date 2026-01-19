package com.pharmaflow.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product extends BaseEntity {

    @Column(nullable = false, unique = true)
    protected String name;

    @Column(nullable = false)
    protected String image;

    protected long quantity;

    @Column(name = "expiry_date", nullable = false)
    protected LocalDateTime expiryDate;

    @Column(name = "expired_status", nullable = false)
    @ColumnDefault(value = "false")
    private boolean expiredStatus = false;

    @Column(name = "near_expired_status", nullable = false)
    @ColumnDefault(value = "false")
    private boolean nearExpiredStatus = false;

    @Column(name = "low_stock", nullable = false)
    @ColumnDefault(value = "false")
    private boolean LowStock = false;

    //price should not be null
    @Column(nullable = false)
    protected BigDecimal price;

    @Column(unique = true)
    protected String barcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    protected Category category;

}
