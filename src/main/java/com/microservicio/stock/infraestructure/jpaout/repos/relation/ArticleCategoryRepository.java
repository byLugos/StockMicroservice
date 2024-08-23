package com.microservicio.stock.infraestructure.jpaout.repos.relation;

import com.microservicio.stock.infraestructure.jpaout.entity.ArticleCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategoryEntity,Long> {

}
