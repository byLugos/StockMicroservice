package com.microservicio.stock.domain.ports.spi;

import com.microservicio.stock.domain.model.Category;

import java.util.List;

public interface CategoryOut {
    boolean existByName(String name);
    Category save(Category category);
    List<Category> findAll();

}
