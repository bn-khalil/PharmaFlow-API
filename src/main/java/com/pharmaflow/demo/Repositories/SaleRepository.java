package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.Sale;
import com.pharmaflow.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
    @Query("""
            select s from Sale s
            where s.saler = :saler
            order by createdAt desc
            """)
    List<Sale> findAllBySalerOrderByCreatedAtDesc(@Param("saler") User saler);
}
