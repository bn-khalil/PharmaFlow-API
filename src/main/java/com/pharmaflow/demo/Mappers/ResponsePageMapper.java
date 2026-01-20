package com.pharmaflow.demo.Mappers;

import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Dto.ResponsePage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ResponsePageMapper {
    @Mapping(source = "content", target = "data")
    @Mapping(source = "number", target = "currentPage")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalItems")
    @Mapping(source = "size", target = "pageSize")

    @Mapping(target = "hasNext", source = ".", qualifiedByName = "mapHasNext")
    @Mapping(target = "hasPrevious", source = ".", qualifiedByName = "mapHasPrevious")
    ResponsePage<ProductDto> toResponsePage(Page<ProductDto> productDtoPage);

    @Named("mapHasNext")
    default boolean mapHasNext(Page<?> page) {
        return page.hasNext();
    }

    @Named("mapHasPrevious")
    default boolean mapHasPrevious(Page<?> page) {
        return page.hasPrevious();
    }
}
