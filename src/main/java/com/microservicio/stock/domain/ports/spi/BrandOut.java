package com.microservicio.stock.domain.ports.spi;

import com.microservicio.stock.domain.model.Brand;

public interface BrandOut {
    boolean existsByNombre(String nombre);
    Brand save(Brand marca);
}
