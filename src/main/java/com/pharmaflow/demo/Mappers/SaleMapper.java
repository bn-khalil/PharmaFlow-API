package com.pharmaflow.demo.Mappers;

import com.pharmaflow.demo.Dto.SaleDto;
import com.pharmaflow.demo.Entities.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SaleItemMapper.class})
public interface SaleMapper {
    @Mapping(target = "saleItemsDtos", source = "saleItems")
    @Mapping(target = "salerName", source = "saler.lastName")
    SaleDto toDto (Sale sale);
}