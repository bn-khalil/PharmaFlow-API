package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.AuditDto;
import com.pharmaflow.demo.Dto.AuthResponse;
import com.pharmaflow.demo.Dto.UserRegister;
import com.pharmaflow.demo.Services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/")
    public ResponseEntity<List<AuditDto>> getAllAudits() {
        return ResponseEntity.status(HttpStatus.OK).body(this.auditService.getAllAudits());
    }
}
