package com.microservicio.stock.infraestructure.jpaout.mapper;

import com.microservicio.stock.domain.model.ArticleCategory;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleCategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JpaArticleCategoryMapper {

    ArticleCategory toDomain(ArticleCategoryEntity articleCategoryEntity);

    ArticleCategoryEntity toEntity(ArticleCategory articleCategory);
}
