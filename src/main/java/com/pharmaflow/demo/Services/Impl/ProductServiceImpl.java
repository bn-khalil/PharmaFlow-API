package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Mappers.ProductMapper;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(
            ProductMapper productMapper,
            ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> products = this.productRepository.findAll();
        return null;
    }

    @Override
    public Product getProductById(UUID productId) {
        return null;
    }

    @Override
    public void createProduct(Product product) {

    }
}
