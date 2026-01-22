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
@Table(name = "products", indexes = {
        @Index(name = "index_barcode", columnList = "barcode"),
        @Index(name = "index_name", columnList = "name")
},
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name", "category_id", "size", "dosage_unit"})
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product extends BaseEntity {

    @Column(nullable = false)
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

    @Column(name = "active", nullable = false)
    @ColumnDefault(value = "true")
    private boolean active = true;

    @Column(nullable = false)
    protected BigDecimal price;

    @Column(unique = true)
    protected String barcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    protected Category category;
}
