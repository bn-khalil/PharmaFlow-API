package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.CategoryDto;
import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Exceptions.BadRequestException;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.CategoryMapper;
import com.pharmaflow.demo.Repositories.CategoryRepository;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @CachePut(value = "category_dto", key = "#result.id")
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return this.categoryMapper.toDto(
                this.categoryRepository.save(
                        this.categoryMapper.toEntity(categoryDto)
                )
        );
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return this.categoryMapper.toDto(this.categoryRepository.findAll());
    }

    @Override
    @Cacheable(value = "category_dto", key = "#categoryId")
    public CategoryDto getCategoryById(UUID categoryId) {
        return this.categoryMapper.toDto(
                this.categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category Not Found!")
        ));
    }

    @Override
    @Transactional
    @CacheEvict(value = "category_dto", key = "#categoryId")
    public void deleteCategory(UUID categoryId) {
        Category defaultCategory = categoryRepository.findByName("generalCategory").orElseThrow(
                () -> new ResourceNotFoundException("General Category Not Found")
        );
        if (defaultCategory.getId().equals(categoryId)) {
            throw new BadRequestException("Cannot delete the default category");
        }
        productRepository.changeAllProductCategory(categoryId, defaultCategory);
        this.categoryRepository.deleteById(categoryId);
    }
}
