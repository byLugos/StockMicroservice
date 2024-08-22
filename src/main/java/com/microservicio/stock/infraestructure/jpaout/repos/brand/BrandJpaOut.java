package com.microservicio.stock.infraestructure.jpaout.repos.brand;

import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.ports.spi.BrandOut;
import com.microservicio.stock.infraestructure.jpaout.entity.BrandEntity;
import com.microservicio.stock.infraestructure.jpaout.mapper.JpaBrandMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class BrandJpaOut implements BrandOut {

    private final BrandRepository brandRepository;
    private final JpaBrandMapper jpaBrandMapper;


    @Override
    public boolean existsByName(String name) {
        return brandRepository.existsByName(name);
    }

    @Override
    public Brand save(Brand brand) {
        BrandEntity brandEntity = jpaBrandMapper.toEntity(brand);
        BrandEntity savedBrand = brandRepository.save(brandEntity);
        return jpaBrandMapper.toDomain(savedBrand);
    }
    @Override
    public List<Brand> findAll() {
        List<BrandEntity> entities = brandRepository.findAll();
        return entities.stream()
                .map(jpaBrandMapper::toDomain)
                .toList();
    }

}
