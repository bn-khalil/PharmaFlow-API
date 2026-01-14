package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok().body(this.productService.getAllProducts());
    }

    @GetMapping("/{product_id}")
    ResponseEntity<ProductDto> getAllProducts(@PathVariable UUID product_id) {
        return ResponseEntity.ok().body(this.productService.getProductById(product_id));
    }
}
