package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.AuditDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AuditService {
    ResponsePage<AuditDto> getAllAudits(Pageable pageable);
    List<AuditDto> getAllAuditsByResponsibleEmail(String email);
    AuditDto getAuditById(UUID auditId);
    AuditDto createAudit(AuditDto auditDto);
}
