package com.microservicio.stock.infraestructure.jpaout.mapper;

import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.infraestructure.jpaout.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JpaBrandMapper {

    BrandEntity toEntity(Brand brand);
    Brand toDomain(BrandEntity brandEntity);
}
