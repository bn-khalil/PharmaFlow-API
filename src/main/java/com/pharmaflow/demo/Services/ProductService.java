package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {
    List<Product> getAllProduct();
    Product getProductById(UUID productId);
    void createProduct(Product product);
}
