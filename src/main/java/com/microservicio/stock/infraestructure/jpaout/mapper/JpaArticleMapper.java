package com.microservicio.stock.infraestructure.jpaout.mapper;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {JpaArticleCategoryMapper.class})
public interface JpaArticleMapper {

    ArticleEntity toEntity(Article article);

    @Mapping(target = "categories", source = "categories")
    Article toDomain(ArticleEntity articleEntity);
}