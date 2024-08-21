package com.microservicio.stock.application.mapper;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.domain.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toEntity(BrandDTO brandDTO);
    BrandDTO toDTO(Brand brand);
}
