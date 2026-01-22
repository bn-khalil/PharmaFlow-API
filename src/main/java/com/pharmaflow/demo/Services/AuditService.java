package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.AuditDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Enums.Action;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AuditService {
    ResponsePage<AuditDto> getAllAudits(String q, LocalDate at, Pageable pageable);
    List<AuditDto> getAllAuditsByResponsibleEmail(String email);
    AuditDto getAuditById(UUID auditId);
    void createAudit(String productName, long quantity, Action action, long before, long after);
}
