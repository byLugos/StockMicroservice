package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.mapper.CategoryMapper;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.api.CategoryIn;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public PageCustom<CategoryDTO> listCategories(PageRequestCustom pageRequestCustom) {
        PageCustom<Category> categoryPage = categoryIn.listCategory(pageRequestCustom);
        List<CategoryDTO> dtoList = categoryPage.getContent().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
        return new PageCustom<>(dtoList, categoryPage.getTotalElements(), categoryPage.getTotalPages(), categoryPage.getCurrentPage(),categoryPage.isAscending());
    }
}
