package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.mapper.CategoryMapper;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.api.CategoryIn;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategotyHandler {

    private final CategoryIn categoryIn;
    private final CategoryMapper categoryMapper;

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = categoryMapper.toEntity(categoryDTO);

        Category newCategory = categoryIn.createCategory(category.getName(),category.getDescription());

        return categoryMapper.toDTO(newCategory);
    }
}
