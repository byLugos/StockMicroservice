package com.microservicio.stock.infraestructure.jpaout.repos.article;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {
    boolean existsByName(String name);
}
