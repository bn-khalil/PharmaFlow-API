package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.AuditDto;

import java.util.List;
import java.util.UUID;

public interface AuditService {
    List<AuditDto> getAllAudits();
    List<AuditDto> getAllAuditsByResponsibleEmail(String email);
    AuditDto getAuditById(UUID auditId);
    AuditDto createAudit(AuditDto auditDto);
}
