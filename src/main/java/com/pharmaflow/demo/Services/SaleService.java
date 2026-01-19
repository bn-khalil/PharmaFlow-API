package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.SaleDto;

import java.util.List;
import java.util.UUID;

public interface SaleService {
    SaleDto createSale(SaleDto saleDto);
    List<SaleDto> getAllSales();
    List<SaleDto> getAllSalesByUser();
    SaleDto getSaleById(UUID saleId);
}
