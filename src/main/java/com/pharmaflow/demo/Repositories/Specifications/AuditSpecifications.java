package com.pharmaflow.demo.Repositories.Specifications;

import com.pharmaflow.demo.Entities.Audit;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AuditSpecifications {
    public static Specification<Audit> hasProductName(String productName) {
        if (productName == null || productName.isEmpty())
            return null;
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), "%" + productName + "%");
    }

    public static Specification<Audit> hasEmail(String email) {
        if (email == null || email.isEmpty())
            return null;
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("responsibleEmail")), "%" + email + "%");
    }

    public static Specification<Audit> atDate(LocalDate at) {
        LocalDateTime startOfDay = at.atStartOfDay();
        LocalDateTime endOfDay = at.atTime(LocalTime.MAX);

        return (root, query, criteriaBuilder)
                -> criteriaBuilder.between(root.get("createdAt"), startOfDay, endOfDay);
    }
}
