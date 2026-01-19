package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("""
            select p from Product p
            LEFT join fetch p.category
            order by createdAt desc
            """)
    List<Product> getAllProducts();

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
            """)
    List<Product> getExpiredProduct();

    @Query("""
            SELECT p from Product p
            WHERE p.expiryDate
            BETWEEN CURRENT_DATE AND :leftDate
            AND p.nearExpiredStatus = false
            """)
    List<Product> getNearExpiredProduct(@Param("leftDate") LocalDateTime leftDate);
}
