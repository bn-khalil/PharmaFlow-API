package com.pharmaflow.demo.Mappers;

import com.pharmaflow.demo.Dto.AuditDto;
import com.pharmaflow.demo.Entities.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditDto toDto(Audit audit);
}
