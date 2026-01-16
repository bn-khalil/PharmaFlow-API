package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleItemRepository extends JpaRepository<SaleItem, UUID> {
}
