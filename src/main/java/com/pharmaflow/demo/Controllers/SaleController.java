package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.SaleDto;
import com.pharmaflow.demo.Services.SaleService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/all")
    public ResponseEntity<List<SaleDto>> listAllSales() {
        return ResponseEntity.status(HttpStatus.OK).body(this.saleService.getAllSales());
    }

    @GetMapping("/user")
    public ResponseEntity<List<SaleDto>> listAllSalesByUser() {
        return ResponseEntity.status(HttpStatus.OK).body(this.saleService.getAllSalesByUser());
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<SaleDto> listAllSaleById(@PathVariable UUID saleId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.saleService.getSaleById(saleId));
    }

    @PostMapping
    public ResponseEntity<SaleDto> createSale(@RequestBody SaleDto saleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.saleService.createSale(saleDto));
    }
}
