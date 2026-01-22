package com.pharmaflow.demo.Mappers;

import com.pharmaflow.demo.Dto.CategoryDto;
import com.pharmaflow.demo.Entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto (Category category);
    Category toEntity (CategoryDto categoryDto);

    List<CategoryDto> toDto (List<Category> category);
}
