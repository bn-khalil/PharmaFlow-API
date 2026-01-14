package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Mappers.ProductMapper;
import com.pharmaflow.demo.Repositories.CategoryRepository;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(
            ProductMapper productMapper,
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        return this.productMapper.toDtoList(products);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        Product product = this.productRepository.getProductById(productId).orElseThrow(
                () -> new RuntimeException("user not found!")
        );
        return this.productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = this.productMapper.toEntity(productDto);
        if (product == null)
            throw new RuntimeException("user not found!");
        Category category = this.categoryRepository.findByName(productDto.getCategory()).orElseThrow(
                () -> new RuntimeException("category not found!")
        );
        product.setCategory(category);
        product = this.productRepository.save(product);
        productDto.setId(product.get);
        return productDto;
    }
}
