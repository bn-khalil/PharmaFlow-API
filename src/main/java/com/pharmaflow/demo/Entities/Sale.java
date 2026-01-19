package com.pharmaflow.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales")
public class Sale extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User saler;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sale")
    List<SaleItem> saleItems;

    @Column(name = "items_number")
    @ColumnDefault(value = "0")
    private long itemsNumber = 0;
}
