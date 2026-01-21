package com.pharmaflow.demo.Repositories.Specifications;

import com.pharmaflow.demo.Entities.MedicalSupple;
import com.pharmaflow.demo.Entities.Medicine;
import com.pharmaflow.demo.Entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->{
            if (name == null || name.isEmpty())
                return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name + "%");
        };
    }

    public static Specification<Product> hasQrcode(String barcode) {
        if (barcode == null || barcode.isEmpty())
            return null;
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("barcode"), barcode);
    }

    public static Specification<Product> hasSize(Long size) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(criteriaBuilder.treat(root, MedicalSupple.class).get("size"), size);
    }

    public static Specification<Product> hasDosageUnit(Long hasDosageUnit) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(criteriaBuilder.treat(root, Medicine.class).get("dosageUnit"), hasDosageUnit);
    }
}
