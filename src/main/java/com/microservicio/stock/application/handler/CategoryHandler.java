package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.mapper.CategoryMapper;
import com.microservicio.stock.application.mapper.PageMapper;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.api.CategoryIn;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class CategoryHandler {
    private final CategoryIn categoryIn;
    private final CategoryMapper categoryMapper;
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        Category newCategory = categoryIn.createCategory(category.getName(),category.getDescription());
        return categoryMapper.toDTO(newCategory);
    }

    public Page<CategoryDTO> listCategories(PageRequestCustom pageRequestCustom, String name, String sort) {
        // Realizar la búsqueda y ordenamiento con los parámetros proporcionados
        PageCustom<Category> pageCustom = categoryIn.listCategory(pageRequestCustom, name, sort);
        // Convertir la página personalizada en una página de Spring
        return PageMapper.toSpringPage(
                new PageCustom<>(
                        pageCustom.getContent().stream().map(categoryMapper::toDTO).toList(),
                        pageCustom.getTotalElements(),
                        pageCustom.getTotalPages(),
                        pageCustom.getCurrentPage(),
                        pageCustom.isAscending()
                )
        );
    }

}
