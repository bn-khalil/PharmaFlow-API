package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.AuditDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Dto.SaleDto;
import com.pharmaflow.demo.Dto.SaleItemsDto;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Entities.Sale;
import com.pharmaflow.demo.Entities.SaleItem;
import com.pharmaflow.demo.Enums.Action;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.SaleMapper;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Repositories.SaleRepository;
import com.pharmaflow.demo.Security.UserSecurity;
import com.pharmaflow.demo.Services.AuditService;
import com.pharmaflow.demo.Services.ProductService;
import com.pharmaflow.demo.Services.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final SaleMapper saleMapper;
    private final AuditService auditService;

    @Override
    @Transactional
    public SaleDto createSale(SaleDto saleDto) {

        if (saleDto == null || saleDto.saleItemsDtos() == null || saleDto.saleItemsDtos().isEmpty())
            throw new ResourceNotFoundException("sale should have at least one sale item");

        Sale sale = new Sale();
        BigDecimal[] total = {BigDecimal.ZERO};

        UserSecurity userSecurity = (UserSecurity)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        sale.setSaler(userSecurity.getUser());
        sale.setItemsNumber(saleDto.saleItemsDtos().size());

        Map<UUID, Long> mergedItems = new HashMap<>();

        for (SaleItemsDto item : saleDto.saleItemsDtos()) {
            UUID id = item.productId();
            long currentQty = mergedItems.getOrDefault(id, 0L);
            mergedItems.put(id, currentQty + item.quantity());
        }

        List<SaleItem> list = mergedItems.entrySet().stream().map( entry -> {
            UUID productId = entry.getKey();
            long quantity = entry.getValue();

            Product product = this.productRepository.getProductById(productId).orElseThrow(
                    () -> new ResourceNotFoundException("Product Not Found!")
            );

            BigDecimal itempTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            total[0] = total[0].add(itempTotal);

            SaleItem newItem = new SaleItem();
            newItem.setProduct(product);
            newItem.setSale(sale);
            newItem.setPriceAtSale(product.getPrice());
            newItem.setQuantity(quantity);

            long before = product.getQuantity();
            long after = before - quantity;

            AuditDto auditDto = AuditDto.builder()
                    .action(Action.SALE)
                    .productName(newItem.getProduct().getName())
                    .quantity(newItem.getQuantity())
                    .responsibleEmail(userSecurity.getUsername())
                    .stockBefore(before)
                    .stockAfter(after)
                    .build();

            this.productService.reduceStock(productId, quantity);
            this.auditService.createAudit(auditDto);
            return newItem;
        }).toList();

        sale.setSaleItems(list);
        sale.setTotalAmount(total[0]);
        return this.saleMapper.toDto(this.saleRepository.save(sale));
    }

    @Override
    public ResponsePage<SaleDto> getAllSales(Pageable pageable) {
        Page<SaleDto> salesDtoPage = this.saleRepository
                .findAll(pageable)
                .map(this.saleMapper::toDto);
        return ResponsePage.fromPage(salesDtoPage);
    }

    @Override
    public ResponsePage<SaleDto> getAllSalesByUser(Pageable pageable) {
        UserSecurity userSecurity = (UserSecurity)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Page<SaleDto> salesDtoPage = this.saleRepository
                .findAllBySaler(userSecurity.getUser(), pageable)
                .map(this.saleMapper::toDto);
        return ResponsePage.fromPage(salesDtoPage);
    }

    @Override
    public SaleDto getSaleById(UUID saleId) {
        return this.saleMapper.toDto(this.saleRepository.findById(saleId).orElseThrow(
                () -> new ResourceNotFoundException("Sale Not Found")
        ));
    }
}
