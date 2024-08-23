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
import org.springframework.data.domain.Pageable;
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

    public Page<CategoryDTO> listCategories(Pageable pageable) {
        //parsear Pageable Spring a PageRequestCustom
        PageRequestCustom pageRequestCustom = new PageRequestCustom(pageable.getPageNumber(),pageable.getPageSize(),pageable.getSort().isSorted());
        //usar la interfaz de dominio
        PageCustom<Category> pageCustom = categoryIn.listCategory(pageRequestCustom);
        //convertir PageCustom a Page de Spring y mapearDTOS
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
