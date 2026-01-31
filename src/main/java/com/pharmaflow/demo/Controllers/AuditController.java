package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.*;
import com.pharmaflow.demo.Services.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/audit")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Audit Management", description = "endpoints for trucking and viewing system audit logs")
public class AuditController {
    private final AuditService auditService;

    @Operation(
            summary = "list audits",
            description = "get all audits for each action with search by name creator or date"
    )
    @GetMapping
    public ResponseEntity<ResponsePage<AuditDto>> getAllAudits(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) LocalDate at,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.auditService.getAllAudits(q, at, pageable));
    }
}
