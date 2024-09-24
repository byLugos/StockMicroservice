package com.microservicio.stock.infraestructure.jpaout.repos.article;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {
    boolean existsByName(String name);
    Optional<ArticleEntity> findByName(String name);

}
