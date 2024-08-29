package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.api.CategoryIn;
import com.microservicio.stock.domain.ports.spi.CategoryOut;
import com.microservicio.stock.domain.util.CategoryValidator;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import com.microservicio.stock.domain.util.pageable.PagingUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService implements CategoryIn {
    private final CategoryOut categoryOut;
    public CategoryService(CategoryOut categoryOut) {
        this.categoryOut = categoryOut;
    }
    @Override
    public Category createCategory(String name, String description) {
        CategoryValidator.validateName(name);
        CategoryValidator.validateDescription(description);
        if (categoryOut.existByName(name)) {
            String categoryExist = "El nombre de la categoria ya existe";
            throw new InvalidNameExceptionMe(categoryExist);
        }
        Category newCategory = new Category(null, name, description);
        return categoryOut.save(newCategory);
    }

    @Override
    public PageCustom<Category> listCategory(PageRequestCustom pageRequestCustom, String name, String sort) {
        List<Category> allCategories = categoryOut.findAll();

        // Filtrar por nombre si se proporciona
        if (name != null && !name.isEmpty()) {
            allCategories = allCategories.stream()
                    .filter(category -> category.getName().contains(name))
                    .collect(Collectors.toList());
        }

        // Ordenar los elementos con la función de utilidad existente
        return PagingUtil.paginateAndSort(allCategories, pageRequestCustom, category -> {
            if ("name".equalsIgnoreCase(sort)) {
                return category.getName();
            }
            // Añadir otros campos de ordenamiento si se necesitan
            return category.getName(); // Default: Ordenar por nombre
        });
    }
}
