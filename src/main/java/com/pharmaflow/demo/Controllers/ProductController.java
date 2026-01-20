package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Services.ProductService;
import jakarta.validation.constraints.Positive;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    ResponseEntity<ResponsePage<ProductDto>> getAllProducts(
            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ResponsePage<ProductDto> responsePage = this.productService.getAllProducts(pageable);
        return ResponseEntity.ok().body(responsePage);
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDto> getAllProducts(@PathVariable(name = "productId") UUID productId) {
        return ResponseEntity.ok().body(this.productService.getProductById(productId));
    }

    @GetMapping("/category/{categoryId}")
    ResponseEntity<List<ProductDto>> getAllProductsByCategory(@PathVariable(name = "categoryId") UUID categoryId) {
        return ResponseEntity.ok().body(this.productService.getProductByCategory(categoryId));
    }

    @PostMapping("/")
    ResponseEntity<ProductDto> createMedicine(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.createProduct(productDto));
    }

    @PatchMapping("/{productId}/add-stock")
    ResponseEntity<ProductDto> addStock(
            @PathVariable(name = "productId") UUID productId,
            @RequestParam @Positive long quantity) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.productService.addStock(productId, quantity)
        );
    }
}
