package com.pharmaflow.demo.Entities;

import com.pharmaflow.demo.Enums.Action;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audits",
        indexes = {
                @Index(name = "index_audit_product_name", columnList = "productName"),
                @Index(name = "index_audit_responsible_email", columnList = "responsibleEmail"),
                @Index(name = "index_audit_created_at", columnList = "createdAt"),
        }
)
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
