package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByCategory(Category category, Pageable pageable);

    @Query("""
            SELECT p from Product p
            LEFT join fetch p.category
            WHERE p.id = :product_id
            """)
    Optional<Product> getProductById(@Param("product_id") UUID product_id);

    @Query("""
            SELECT p from Product p
            WHERE p.expiryDate <= CURRENT_DATE
            AND p.expiredStatus = false
            order by p.createdAt desc
            """)
    List<Product> getExpiredProduct();

    @Query("""
            SELECT p from Product p
            WHERE p.expiryDate
            BETWEEN CURRENT_DATE AND :leftDate
            AND p.nearExpiredStatus = false
            order by p.createdAt desc
            """)
    List<Product> getNearExpiredProduct(@Param("leftDate") LocalDateTime leftDate);
}
