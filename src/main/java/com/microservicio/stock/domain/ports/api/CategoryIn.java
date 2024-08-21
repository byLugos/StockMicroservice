package com.microservicio.stock.domain.ports.api;

import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;

public interface CategoryIn {
    Category createCategory(String name, String description);
    PageCustom<Category> listCategory(PageRequestCustom pageRequestCustom);
}
