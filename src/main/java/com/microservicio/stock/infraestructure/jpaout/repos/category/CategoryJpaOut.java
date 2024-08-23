package com.microservicio.stock.infraestructure.jpaout.repos.category;

import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.spi.CategoryOut;
import com.microservicio.stock.infraestructure.jpaout.entity.CategoryEntity;
import com.microservicio.stock.infraestructure.jpaout.mapper.JpaCategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@AllArgsConstructor
@Component
public class CategoryJpaOut implements CategoryOut {

    private final CategoryRepository categoryRepository;
    private final JpaCategoryMapper jpaCategoryMapper;


    @Override
    public boolean existByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public Category save(Category category) {

        CategoryEntity entity = jpaCategoryMapper.toEntity(category);

        CategoryEntity savedEntity = categoryRepository.save(entity);

        return jpaCategoryMapper.toDomain(savedEntity);
    }

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> entities = categoryRepository.findAll();
        return entities.stream()
                .map(jpaCategoryMapper::toDomain)
                .toList();
    }
}

