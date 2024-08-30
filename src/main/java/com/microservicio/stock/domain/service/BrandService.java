package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.api.BrandIn;
import com.microservicio.stock.domain.ports.spi.BrandOut;
import com.microservicio.stock.domain.util.BrandValidator;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import com.microservicio.stock.domain.util.pageable.PagingUtil;

import java.util.List;

public class BrandService implements BrandIn {
    private final BrandOut brandOut;
    public BrandService(BrandOut brandOut){
        this.brandOut = brandOut;
    }

    @Override
    public Brand createBrand(String name, String description) {
        BrandValidator.validateName(name);
        BrandValidator.validateDescription(description);
        if (brandOut.existsByName(name)){
            String brandExist = "El nombre de la marca ya existe";
            throw new InvalidNameExceptionMe(brandExist);
        }
        Brand newBrand = new Brand(null, name,description);
        return brandOut.save(newBrand);
    }

    @Override
    public PageCustom<Brand> listBrand(PageRequestCustom pageRequestCustom,String name, String sort) {
        List<Brand> allBrands = brandOut.findAll();
        if (name != null && !name.isEmpty()) {
            allBrands = allBrands.stream()
                    .filter(brand -> brand.getName().contains(name))
                    .toList();
        }
        return PagingUtil.paginateAndSort(allBrands, pageRequestCustom, brand -> {
            if ("name".equalsIgnoreCase(sort)) {
                return brand.getName();
            }
            return brand.getName();
        });
    }
}
