package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.SaleDto;
import com.pharmaflow.demo.Dto.SaleItemsDto;
import com.pharmaflow.demo.Entities.Audit;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Entities.Sale;
import com.pharmaflow.demo.Entities.SaleItem;
import com.pharmaflow.demo.Enums.Action;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.SaleItemMapper;
import com.pharmaflow.demo.Mappers.SaleMapper;
import com.pharmaflow.demo.Mappers.UserMapper;
import com.pharmaflow.demo.Repositories.AuditRepository;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Repositories.SaleItemRepository;
import com.pharmaflow.demo.Repositories.SaleRepository;
import com.pharmaflow.demo.Security.UserSecurity;
import com.pharmaflow.demo.Services.ProductService;
import com.pharmaflow.demo.Services.SaleService;
import com.pharmaflow.demo.Services.UserService;
import jakarta.transaction.Transactional;
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
    private final AuditRepository auditRepository;

    @Override
    @Transactional
    public SaleDto createSale(SaleDto saleDto) {
        Sale sale = new Sale();
        BigDecimal[] total = {BigDecimal.ZERO};

        UserSecurity userSecurity = (UserSecurity)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        sale.setSaler(userSecurity.getUser());

        Map<UUID, Long> mergedItems = new HashMap<>();

        for (SaleItemsDto item : saleDto.saleItemsDtos()) {
            UUID id = item.productId();
            long currentQty = mergedItems.getOrDefault(id, 0L);
            mergedItems.put(id, currentQty + item.quantity());
        }

        List<SaleItem> list = mergedItems.entrySet().stream().map( entry -> {
            UUID productId = entry.getKey();
            Long quantity = entry.getValue();
            Audit audit = new Audit();

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

            audit.setAction(Action.SALE);
            audit.setProductName(newItem.getProduct().getName());
            audit.setQuantity(newItem.getQuantity());
            audit.setResponsibleEmail(userSecurity.getUsername());

            long before = product.getQuantity();
            long after = before - quantity;

            audit.setStockBefore(before);
            audit.setStockAfter(after);
            this.productService.reduceStock(productId, quantity);
            this.auditRepository.save(audit);
            return newItem;
        }).toList();

        sale.setSaleItems(list);
        sale.setTotalAmount(total[0]);

        return this.saleMapper.toDto(this.saleRepository.save(sale));
    }
}
