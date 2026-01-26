package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Enums.Action;
import com.pharmaflow.demo.Enums.Notify;
import com.pharmaflow.demo.Events.AuditCreatedEvent;
import com.pharmaflow.demo.Events.NotificationEvent;
import com.pharmaflow.demo.Exceptions.InvalidStockException;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.ProductMapper;
import com.pharmaflow.demo.Repositories.CategoryRepository;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Repositories.Specifications.ProductSpecifications;
import com.pharmaflow.demo.Services.AuditService;
import com.pharmaflow.demo.Services.NotificationService;
import com.pharmaflow.demo.Services.ProductService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final NotificationService notificationService;
    private final AuditService auditService;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    @Transactional(readOnly = true)
    public ResponsePage<ProductDto> getAllProducts(
            String search,
            Long size,
            Long dosage,
            boolean archived,
            Pageable pageable
    ) {

        Specification<Product> spec = Specification.<Product>where(
                (root, query, cb) -> cb.conjunction()
        );

        if (search != null && !search.isEmpty())
                spec = spec.and(ProductSpecifications.hasName(search))
                        .or(ProductSpecifications.hasQrcode(search));
        if (size != null)
            spec = spec.and(ProductSpecifications.hasSize(size));
        if (dosage != null)
            spec = spec.and(ProductSpecifications.hasDosageUnit(dosage));

        if (archived)
            spec = spec.and(ProductSpecifications.archived(!archived));
        else
            spec = spec.and(ProductSpecifications.archived(!archived));

        Page<ProductDto> productDtoPage = this.productRepository
                .findAll(spec, pageable)
                .map( product ->  {
                    ProductDto productDto = this.productMapper.toDto(product);
                    String fullImageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/products/images/")
                            .path(product.getImage())
                            .toUriString();
                    productDto.setImageUrl(fullImageUrl);
                    return productDto;
                });

        return ResponsePage.fromPage(productDtoPage);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsePage<ProductDto> getProductByCategory(UUID categoryId, Pageable pageable) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category Not Found")
        );

        Page<ProductDto> productDtoPage = this.productRepository
                .findAllByCategory(category, pageable)
                .map( product ->  {
                    ProductDto productDto = this.productMapper.toDto(product);
                    String fullImageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/products/images/")
                            .path(product.getImage())
                            .toUriString();
                    productDto.setImageUrl(fullImageUrl);
                    return productDto;
                });
        return ResponsePage.fromPage(productDtoPage);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        Product product = this.productRepository.getProductById(productId).orElseThrow(
                () -> new ResourceNotFoundException("product not found!")
        );
        ProductDto productDto = this.productMapper.toDto(product);
        String fullImageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/products/images/")
                .path(product.getImage())
                .toUriString();
        productDto.setImageUrl(fullImageUrl);
        return productDto;
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
        product.setExpiredStatus(false);
        product.setNearExpiredStatus(false);
        product.setActive(true);

        Product savedProduct = this.productRepository.save(product);

        applicationEventPublisher.publishEvent(new NotificationEvent(
                product.getName() + " added to stock",
                Notify.STOCK_ADDED,
                savedProduct
        ));
        return this.productMapper.toDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto addStock(UUID productId, long quantity) {
        Product product = this.productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product Not Found!")
        );

        long before = product.getQuantity();
        product.setQuantity(product.getQuantity() + quantity);
        long after = product.getQuantity();

        Product savedProduct = this.productRepository.save(product);

        applicationEventPublisher.publishEvent(new AuditCreatedEvent(
                product.getName(),
                product.getQuantity(),
                Action.STOCK,
                before,
                after
        ));
        applicationEventPublisher.publishEvent(new NotificationEvent(
                quantity + " units from " + product.getName() + " added to Stock",
                Notify.STOCK_ADDED,
                savedProduct
        ));
        return this.productMapper.toDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto reduceStock(UUID productId, long quantity) {
        Product product = this.productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product Not Found!")
        );
        if (product.getQuantity() < quantity)
            throw new InvalidStockException("Stock not enough for product: " + product.getName());
        product.setQuantity(product.getQuantity() - quantity);
        Product savedProduct = this.productRepository.save(product);
        if (product.getQuantity() < 10) {
            applicationEventPublisher.publishEvent(new NotificationEvent(
                    product.getQuantity() + " items left or less, " + product.getName() + " low in stock",
                    Notify.LOW_STOCK,
                    savedProduct
            ));
        }
        return this.productMapper.toDto(savedProduct);
    }

    @Override
    public void NotifyExpiredProducts() {
        List<Product> expiredProducts = this.productRepository.getExpiredProduct();
        if (expiredProducts == null || expiredProducts.isEmpty())
            return ;
        for (Product product :  expiredProducts) {
            applicationEventPublisher.publishEvent(new NotificationEvent(
                    "Product " + product.getName() + " expired at " +
                            product.getExpiryDate().toLocalDate(),
                    Notify.EXPIRED,
                    product
            ));
        }
    }

    @Override
    public void NotifyNearExpireProducts() {
        LocalDateTime limitDate = LocalDateTime.now()
                .plusDays(30)
                .with(LocalTime.MAX);

        List<Product> expiredProducts = this.productRepository.getNearExpiredProduct(limitDate);
        if (expiredProducts == null || expiredProducts.isEmpty())
            return ;
        for (Product product :  expiredProducts) {
            applicationEventPublisher.publishEvent(new NotificationEvent(
                    "Product " + product.getName() +
                            " will be expired at " +
                            product.getExpiryDate().toLocalDate(),
                    Notify.NEAR_EXPIRY,
                    product
            ));
        }
    }

    @Override
    public ProductDto editProduct(UUID productId, ProductDto productDto) {
        Product product = this.productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product Not Found!")
        );
        this.productMapper.editProduct(productDto, product);
        if (productDto.getCategory() != null
                && !productDto.getCategory().trim().isEmpty()
                && !productDto.getCategory().equals(product.getCategory().getName())) {
            Category category = this.categoryRepository.findByName(productDto.getCategory()).orElseThrow(
                    () -> new ResourceNotFoundException("Category Not Found!")
            );
            product.setCategory(category);
        }
        return this.productMapper.toDto(product);
    }

    @Override
    @Transactional
    public void toggleProduct(UUID productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product Not Found")
        );
        product.setActive(!product.isActive());
    }
}
