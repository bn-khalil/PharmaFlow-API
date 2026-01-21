package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<Audit, UUID>, JpaSpecificationExecutor<Audit> {
}
