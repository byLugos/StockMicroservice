package com.microservicio.stock.application.mapper;

import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CategoryMapper {
    Category toEntity(CategoryDTO categoryDTO);
    CategoryDTO toDTO(Category category);
}
