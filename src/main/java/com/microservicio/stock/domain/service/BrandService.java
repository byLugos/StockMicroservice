package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.ports.api.BrandIn;
import com.microservicio.stock.domain.ports.spi.BrandOut;
import com.microservicio.stock.domain.util.BrandValidator;

public class BrandService implements BrandIn {
    private final BrandOut brandOut;
    public BrandService(BrandOut brandOut){
        this.brandOut = brandOut;
    }

    @Override
    public Brand createBrand(String name, String description) {
        BrandValidator.validateName(name);
        BrandValidator.validateDescription(description);
        if (brandOut.existsByNombre(name)){
            String brandExist = "El nombre de la marca ya existe";
            throw new InvalidNameExceptionMe(brandExist);
        }
        Brand newBrand = new Brand(null, name,description);
        return brandOut.save(newBrand);
    }
}
