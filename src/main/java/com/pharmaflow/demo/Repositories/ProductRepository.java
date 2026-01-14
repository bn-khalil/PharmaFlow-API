package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("select p from Product p left join fetch p.category")
    List<Product> getAllProducts();

    @Query("select p from Product p left join fetch p.category where p.id = :id")
    Optional<Product> getProductById(@Param("id") UUID product_id);
}
