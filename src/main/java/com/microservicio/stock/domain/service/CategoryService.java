package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.api.CategoryIn;
import com.microservicio.stock.domain.ports.spi.CategoryOut;
import com.microservicio.stock.domain.util.CategoryValidator;

public class CategoryService implements CategoryIn {

    private final CategoryOut categoryOut;

    public CategoryService(CategoryOut categoryOut){
        this.categoryOut = categoryOut;
    }

    @Override
    public Category createCategory(String name, String description) {
        CategoryValidator.validateName(name);
        CategoryValidator.validateDescription(description);
        if (categoryOut.existByName(name)){
            String categoryExist = "El nombre de la categoria ya existe";
            throw new InvalidNameExceptionMe(categoryExist);
        }
        Category newCategory = new Category(null, name,description);
        return categoryOut.save(newCategory);
    }
}
