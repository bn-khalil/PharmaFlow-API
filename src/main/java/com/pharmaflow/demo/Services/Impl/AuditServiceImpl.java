package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.AuditDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Entities.Audit;
import com.pharmaflow.demo.Mappers.AuditMapper;
import com.pharmaflow.demo.Repositories.AuditRepository;
import com.pharmaflow.demo.Repositories.Specifications.AuditSpecifications;
import com.pharmaflow.demo.Services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

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

    @Override
    public ResponsePage<AuditDto> getAllAudits(String q, LocalDate at, Pageable pageable) {

        Specification<Audit> spec = Specification.where(
                (root, query, criteriaBuilder)
                        -> criteriaBuilder.conjunction()
        );

        if (q != null)
            spec = spec.and(AuditSpecifications.hasProductName(q))
                    .or(AuditSpecifications.hasEmail(q));
        if (at != null)
            spec = spec.and(AuditSpecifications.atDate(at));
        Page<AuditDto> auditsDtoPage = this.auditRepository
                .findAll(spec, pageable)
                .map(this.auditMapper::toDto);
        return ResponsePage.fromPage(auditsDtoPage);
    }

    @Override
    public List<AuditDto> getAllAuditsByResponsibleEmail(String email) {
        return List.of();
    }

    @Override
    public AuditDto getAuditById(UUID auditId) {
        return null;
    }

}
