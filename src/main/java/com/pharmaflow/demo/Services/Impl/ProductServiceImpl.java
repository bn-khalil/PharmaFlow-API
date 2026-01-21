package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.AuditDto;
import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Enums.Action;
import com.pharmaflow.demo.Enums.Notify;
import com.pharmaflow.demo.Exceptions.InvalidStockException;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.ProductMapper;
import com.pharmaflow.demo.Repositories.CategoryRepository;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Repositories.Specifications.ProductSpecifications;
import com.pharmaflow.demo.Security.UserSecurity;
import com.pharmaflow.demo.Services.AuditService;
import com.pharmaflow.demo.Services.NotificationService;
import com.pharmaflow.demo.Services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    @Transactional(readOnly = true)
    public ResponsePage<ProductDto> getAllProducts(String search, Long size, Long dosage, Pageable pageable) {

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

        Page<ProductDto> productDtoPage = this.productRepository
                .findAll(spec, pageable)
                .map(this.productMapper::toDto);

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
                .map(this.productMapper::toDto);
        return ResponsePage.fromPage(productDtoPage);

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
        product.setExpiredStatus(false);
        product.setNearExpiredStatus(false);
        product = this.productRepository.save(product);
        productDto.setId(product.getId());
        return productDto;
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

        UserSecurity userSecurity = (UserSecurity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        AuditDto auditDto = AuditDto.builder()
                .action(Action.STOCK)
                .productName(product.getName())
                .quantity(product.getQuantity())
                .responsibleEmail(userSecurity.getUsername())
                .stockBefore(before)
                .stockAfter(after)
                .build();
        System.out.println(auditDto);
        this.auditService.createAudit(auditDto);

        String message = quantity + " units from " + product.getName() + " added to Stock";
        this.notificationService.createNotification(message, Notify.STOCK_ADDED, product);

        return this.productMapper.toDto(this.productRepository.save(product));
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
        if (product.getQuantity() < 10) {
            String message = product.getQuantity() + " items left, " + product.getName() + " low in stock";
            this.notificationService.createNotification(message, Notify.LOW_STOCK, product);
        }
        return this.productMapper.toDto(this.productRepository.save(product));
    }

    @Override
    public void NotifyExpiredProducts() {
        List<Product> expiredProducts = this.productRepository.getExpiredProduct();
        if (expiredProducts == null || expiredProducts.isEmpty())
            return ;
        for (Product product :  expiredProducts) {
            String message = "Product " + product.getName() + " expired at " + product.getExpiryDate().toLocalDate();
            this.notificationService.createNotification(message, Notify.EXPIRED, product);
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
            String message = "Product " + product.getName() + " will be expired at " + product.getExpiryDate().toLocalDate();
            this.notificationService.createNotification(message, Notify.NEAR_EXPIRY, product);
        }
    }
}
