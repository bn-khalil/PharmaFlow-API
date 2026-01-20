package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Dto.SaleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SaleService {
    SaleDto createSale(SaleDto saleDto);
    ResponsePage<SaleDto> getAllSales(Pageable pageable);
    ResponsePage<SaleDto> getAllSalesByUser(Pageable pageable);
    SaleDto getSaleById(UUID saleId);
}
