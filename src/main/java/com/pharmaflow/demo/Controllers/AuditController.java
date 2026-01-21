package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.*;
import com.pharmaflow.demo.Services.AuditService;
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
public class AuditController {
    private final AuditService auditService;

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
