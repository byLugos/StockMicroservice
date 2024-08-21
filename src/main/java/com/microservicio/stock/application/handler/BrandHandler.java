package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.mapper.BrandMapper;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.ports.api.BrandIn;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandHandler {

    private final BrandIn brandIn;
    private final BrandMapper brandMapper;

    public BrandDTO createBrand(BrandDTO brandDTO) {

        Brand brand = brandMapper.toEntity(brandDTO);

        Brand newBrand = brandIn.createBrand(brand.getName(),brand.getDescription());

        return brandMapper.toDTO(newBrand);
    }
}
