//package com.pharmaflow.demo.Controllers;
//
//import com.pharmaflow.demo.Dto.SaleDto;
//import com.pharmaflow.demo.Services.SaleService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/sale")
//@RequiredArgsConstructor
//public class SaleController {
//
//    private final SaleService saleService;
//
//    @PostMapping
//    public ResponseEntity<SaleDto> createSale(@RequestBody SaleDto saleDto) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(null);
//    }
//}
