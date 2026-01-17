package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.AuditDto;
import com.pharmaflow.demo.Entities.Audit;
import com.pharmaflow.demo.Mappers.AuditMapper;
import com.pharmaflow.demo.Repositories.AuditRepository;
import com.pharmaflow.demo.Services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    public List<AuditDto> getAllAudits() {
        List<Audit> audits = this.auditRepository.findAll();
        return this.auditMapper.toDto(audits);
    }

    @Override
    public List<AuditDto> getAllAuditsByResponsibleEmail(String email) {
        return List.of();
    }

    @Override
    public AuditDto getAuditById(UUID auditId) {
        return null;
    }

    @Override
    public AuditDto createAudit(AuditDto auditDto) {
        Audit audit = new Audit();
        audit.setResponsibleEmail(auditDto.responsibleEmail());
        audit.setAction(auditDto.action());
        audit.setQuantity(auditDto.quantity());
        audit.setProductName(auditDto.productName());
        audit.setStockBefore(auditDto.stockBefore());
        audit.setStockAfter(auditDto.stockAfter());
        return this.auditMapper.toDto(this.auditRepository.save(audit));
    }
}
