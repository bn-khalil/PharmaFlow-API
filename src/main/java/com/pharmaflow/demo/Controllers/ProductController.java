package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Services.ProductService;
import com.pharmaflow.demo.validation.OnCreate;
import com.pharmaflow.demo.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    ResponseEntity<ResponsePage<ProductDto>> getAllProducts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long size,
            @RequestParam(required = false) Long dosage,
            @RequestParam(required = false, defaultValue = "false") boolean archived,
            @PageableDefault(size = 20, sort = "createdAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok()
                .body(this.productService.getAllProducts(q, size, dosage, archived, pageable));
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDto> getAllProducts(
            @PathVariable(name = "productId") UUID productId
    ) {
        return ResponseEntity.ok()
                .body(this.productService.getProductById(productId));
    }

    @GetMapping("/category/{categoryId}")
    ResponseEntity<ResponsePage<ProductDto>> getAllProductsByCategory(
            @PathVariable(name = "categoryId") UUID categoryId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok()
                .body(this.productService.getProductByCategory(categoryId, pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ProductDto> createProduct(
            @Validated(OnCreate.class)
            @RequestBody
            ProductDto productDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.productService.createProduct(productDto));
    }

    @PatchMapping("/{productId}/add-stock")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ProductDto> addStock(
            @PathVariable(name = "productId") UUID productId,
            @RequestParam @Positive long quantity
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.addStock(productId, quantity)
        );
    }

    @PatchMapping("/{productId}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ProductDto> editProduct(
            @Validated(OnUpdate.class)
            @PathVariable("productId") UUID productId,
            @RequestBody ProductDto productDto
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.editProduct(productId, productDto));
    }

    @PatchMapping("/{productId}/active")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ProductDto> enableOrDisableProduct(
            @PathVariable("productId") UUID productId
    ) {
        this.productService.toggleProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
