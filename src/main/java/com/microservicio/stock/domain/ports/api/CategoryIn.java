package com.microservicio.stock.domain.ports.api;

import com.microservicio.stock.domain.model.Category;

public interface CategoryIn {
    Category createCategory(String name, String description);
}
