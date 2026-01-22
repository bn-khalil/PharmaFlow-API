package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {
    ResponsePage<ProductDto> getAllProducts(String search, Long size, Long dosage, boolean archived, Pageable pageable);
    ResponsePage<ProductDto> getProductByCategory(UUID categoryId, Pageable pageable);
    ProductDto getProductById(UUID productId);
    ProductDto createProduct(ProductDto productDto);
    ProductDto addStock(UUID productId, long quantity);
    ProductDto reduceStock(UUID productId, long quantity);
    ProductDto editProduct(UUID productId, ProductDto productDto);
    void toggleProduct(UUID productId);
    void NotifyExpiredProducts();
    void NotifyNearExpireProducts();
}
