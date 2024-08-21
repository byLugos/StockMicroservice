package com.microservicio.stock.domain.ports.spi;

import com.microservicio.stock.domain.model.Category;

public interface CategoryOut {
    boolean existByName(String name);
    Category save(Category category);

}
