package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.SaleDto;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Entities.Sale;
import com.pharmaflow.demo.Entities.SaleItem;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.SaleItemMapper;
import com.pharmaflow.demo.Mappers.SaleMapper;
import com.pharmaflow.demo.Mappers.UserMapper;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public SaleDto createSale(SaleDto saleDto) {
        Sale sale = new Sale();
        BigDecimal[] total = {BigDecimal.ZERO};

        UserSecurity userSecurity = (UserSecurity)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        sale.setSaler(userSecurity.getUser());

        List<SaleItem> list = saleDto.saleItemsDtos().stream().map( saleItem -> {
            Product product = this.productRepository.getProductById(saleItem.productId()).orElseThrow(
                    () -> new ResourceNotFoundException("Product Not Found!")
            );
            BigDecimal itempTotal = product.getPrice().multiply(BigDecimal.valueOf(saleItem.quantity()));
            total[0] = total[0].add(itempTotal);

            SaleItem newItem = new SaleItem();
            newItem.setProduct(product);
            newItem.setSale(sale);
            newItem.setPriceAtSale(product.getPrice());
            newItem.setQuantity(saleItem.quantity());
            return newItem;
        }).toList();

        sale.setSaleItems(list);
        sale.setTotalAmount(total[0]);
        return this.saleMapper.toDto(this.saleRepository.save(sale));
    }
}
