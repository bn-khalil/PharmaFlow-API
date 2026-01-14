package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.CategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    Optional<CategoryDto> getCategoryById(UUID categoryId);
}
