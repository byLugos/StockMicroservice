package com.microservicio.stock.infraestructure.jpaout.mapper;

import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.infraestructure.jpaout.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JpaCategoryMapper {
    CategoryEntity toEntity(Category category);
    Category toDomain(CategoryEntity categoryEntity);
}
