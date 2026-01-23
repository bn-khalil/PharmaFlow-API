package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.CategoryDto;
import com.pharmaflow.demo.Services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(
            @Valid
            @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.categoryService.createCategory(categoryDto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.categoryService.getAllCategories());
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(
            @PathVariable UUID categoryId
            ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.categoryService.getCategoryById(categoryId));
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable UUID categoryId
    ) {
        this.categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
