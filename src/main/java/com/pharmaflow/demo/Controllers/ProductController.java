package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok().body(this.productService.getAllProducts());
    }

    @GetMapping("/{product_id}")
    ResponseEntity<ProductDto> getAllProducts(@PathVariable(name = "product_id") UUID product_id) {
        return ResponseEntity.ok().body(this.productService.getProductById(product_id));
    }

    @PostMapping("/")
    @PreAuthorize("")
    ResponseEntity<ProductDto> createMedicine(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.createProduct(productDto));
    }
}
