package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.CategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(UUID categoryId);
    void deleteCategory(UUID categoryId);
}
