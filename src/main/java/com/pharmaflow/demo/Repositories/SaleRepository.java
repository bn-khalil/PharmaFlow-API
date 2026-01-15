package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
}
