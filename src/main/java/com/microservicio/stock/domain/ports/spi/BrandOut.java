package com.microservicio.stock.domain.ports.spi;

import com.microservicio.stock.domain.model.Brand;

import java.util.List;

public interface BrandOut {
    boolean existsByName(String nombre);
    Brand save(Brand marca);
    List<Brand> findAll();
}
