package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Exceptions.InvalidStockException;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.ProductMapper;
import com.pharmaflow.demo.Repositories.CategoryRepository;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        return this.productMapper.toDtoList(products);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        Product product = this.productRepository.getProductById(productId).orElseThrow(
                () -> new ResourceNotFoundException("product not found!")
        );
        return this.productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = this.productMapper.toEntity(productDto);
        if (product == null)
            throw new ResourceNotFoundException("product not found!");
        Category category = this.categoryRepository.findByName(productDto.getCategory()).orElseThrow(
                () -> new ResourceNotFoundException("category not found!")
        );
        product.setCategory(category);
        product = this.productRepository.save(product);
        productDto.setId(product.getId());
        return productDto;
    }

    @Override
    public ProductDto addStock(UUID productId, long quantity) {
        Product product = this.productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product Not Found!")
        );
        product.setQuantity(product.getQuantity() + quantity);
        return this.productMapper.toDto(this.productRepository.save(product));
    }

    @Override
    public ProductDto reduceStock(UUID productId, long quantity) {
        Product product = this.productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product Not Found!")
        );
        if (product.getQuantity() < quantity)
            throw new InvalidStockException("Stock not enough for product: " + product.getName());
        product.setQuantity(product.getQuantity() - quantity);
        return this.productMapper.toDto(this.productRepository.save(product));
    }
}
