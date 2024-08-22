package com.microservicio.stock.domain.ports.api;

import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;

public interface BrandIn {
    Brand createBrand(String nombre, String descripcion);
    PageCustom<Brand> listBrand(PageRequestCustom pageRequestCustom);
}
