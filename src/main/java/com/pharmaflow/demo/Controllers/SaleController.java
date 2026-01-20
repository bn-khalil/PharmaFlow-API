package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Dto.SaleDto;
import com.pharmaflow.demo.Services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    public ResponseEntity<ResponsePage<SaleDto>> listAllSales(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.saleService.getAllSales(pageable));
    }

    @GetMapping("/user")
    public ResponseEntity<ResponsePage<SaleDto>> listAllSalesByUser(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.saleService.getAllSalesByUser(pageable));
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<SaleDto> listAllSaleById(@PathVariable UUID saleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.saleService.getSaleById(saleId));
    }

    @PostMapping
    public ResponseEntity<SaleDto> createSale(@RequestBody SaleDto saleDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.saleService.createSale(saleDto));
    }
}
