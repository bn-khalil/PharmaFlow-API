package com.pharmaflow.demo.Mappers;

import com.pharmaflow.demo.Dto.MedicalSuppleDto;
import com.pharmaflow.demo.Dto.MedicineDto;
import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Entities.MedicalSupple;
import com.pharmaflow.demo.Entities.Medicine;
import com.pharmaflow.demo.Entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {

    default ProductDto toDto(Product product) {
        if (product == null)
            return null;
        if (product instanceof Medicine med)
            return toMedicineDto(med);
        if (product instanceof MedicalSupple meds)
            return toMedicalSuppleDto(meds);
        return null;
    }

    @Mapping(target = "category", source = "category.name")
    MedicineDto toMedicineDto(Medicine medicine);

    @Mapping(target = "category", source = "category.name")
    MedicalSuppleDto toMedicalSuppleDto(MedicalSupple medicalSupple);


    default Product toEntity(ProductDto productDto) {
        if (productDto == null)
            return null;
        if (productDto instanceof MedicineDto med)
            return toMedicineEntity(med);
        if (productDto instanceof MedicalSuppleDto meds)
            return toMedicalSuppleEntity(meds);
        return null;
    }

    @Mapping(target = "category", ignore = true)
    Medicine toMedicineEntity(MedicineDto medicineDto);

    @Mapping(target = "category", ignore = true)
    MedicalSupple toMedicalSuppleEntity(MedicalSuppleDto medicalSuppleDto);

    List<ProductDto> toDtoList(List<Product> products);

    List<Product> toEntityList(List<ProductDto> pdtos);
}
