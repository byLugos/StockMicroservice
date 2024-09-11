package com.microservicio.stock.domain.ports.api;

import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.pageable.PageCustom;
import com.microservicio.stock.domain.pageable.PageRequestCustom;

public interface BrandIn {
    Brand createBrand(String nombre, String descripcion);
    PageCustom<Brand> listBrand(PageRequestCustom pageRequestCustom, String name, String sort);
}
