package com.microservicio.stock.domain.ports.api;

import com.microservicio.stock.domain.model.Brand;

public interface BrandIn {
    Brand createBrand(String nombre, String descripcion);
}
