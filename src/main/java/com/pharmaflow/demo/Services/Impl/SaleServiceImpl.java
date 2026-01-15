package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.SaleDto;
import com.pharmaflow.demo.Entities.Sale;
import com.pharmaflow.demo.Repositories.SaleRepository;
import com.pharmaflow.demo.Services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Override
    public SaleDto createSale(SaleDto saleDto) {
//        Sale sale = new Sale();

        return null;
    }
}
