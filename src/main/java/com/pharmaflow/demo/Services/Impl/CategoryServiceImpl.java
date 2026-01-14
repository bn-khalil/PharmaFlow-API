package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.CategoryDto;
import com.pharmaflow.demo.Services.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<CategoryDto> getAllCategories() {
        return List.of();
    }

    @Override
    public Optional<CategoryDto> getCategoryById(UUID categoryId) {
        return Optional.empty();
    }
}
