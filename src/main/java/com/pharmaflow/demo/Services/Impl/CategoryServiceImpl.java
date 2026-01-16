package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.CategoryDto;
import com.pharmaflow.demo.Mappers.CategoryMapper;
import com.pharmaflow.demo.Repositories.CategoryRepository;
import com.pharmaflow.demo.Services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return this.categoryMapper.toDto(
                this.categoryRepository.save(
                        this.categoryMapper.toEntity(categoryDto)
                )
        );
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return List.of();
    }

    @Override
    public Optional<CategoryDto> getCategoryById(UUID categoryId) {
        return Optional.empty();
    }
}
