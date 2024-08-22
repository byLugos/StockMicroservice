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
    public PageCustom<Category> listCategory(PageRequestCustom pageRequestCustom) {
        List<Category> allCategories = categoryOut.findAll();
        return PagingUtil.paginateAndSort(allCategories, pageRequestCustom, Category::getName);
    }
}
