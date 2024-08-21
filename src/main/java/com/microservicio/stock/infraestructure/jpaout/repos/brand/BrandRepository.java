package com.microservicio.stock.infraestructure.jpaout.repos.brand;

import com.microservicio.stock.infraestructure.jpaout.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity,Long> {
    boolean existsByName(String name);
}
