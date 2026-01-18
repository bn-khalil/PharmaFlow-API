package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(UUID productId);
    ProductDto createProduct(ProductDto productDto);
    ProductDto addStock(UUID productId, long quantity);
    ProductDto reduceStock(UUID productId, long quantity);
    void NotifyExpiredProducts();
    void NotifyNearExpireProducts();
}
