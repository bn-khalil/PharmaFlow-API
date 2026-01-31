package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import com.pharmaflow.demo.Services.FileUploadService;
import com.pharmaflow.demo.Services.ProductService;
import com.pharmaflow.demo.validation.OnCreate;
import com.pharmaflow.demo.validation.OnUpdate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products Management", description = "Endpoints to handle product inventory, stock updates and browsing")
public class ProductController {

    private final ProductService productService;
    private final FileUploadService fileUploadService;

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

    @GetMapping("/images/{filename:.+}")
    ResponseEntity<Resource> getProductImage(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get("uploads/products").resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            String fileExtensionType = Files.probeContentType(filePath);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, fileExtensionType)
                    .body(resource);
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDto> getProductById(
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ProductDto> createProduct(
            @Validated(OnCreate.class)
            @RequestPart ProductDto productDto,
            @RequestPart MultipartFile image
            ) throws IOException {

            String imageName = fileUploadService.saveFile(image);
        try {
            productDto.setImage(imageName);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(this.productService.createProduct(productDto));
        } catch (Exception e) {
            fileUploadService.deleteFile(imageName);
            throw e;
        }
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
