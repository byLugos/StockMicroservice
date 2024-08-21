package com.microservicio.stock.infraestructure.jpaout.repos.category;

import com.microservicio.stock.infraestructure.jpaout.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);
}
