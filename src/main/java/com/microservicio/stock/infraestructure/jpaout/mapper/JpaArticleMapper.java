package com.microservicio.stock.infraestructure.jpaout.mapper;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {JpaArticleCategoryMapper.class, JpaBrandMapper.class})
public interface JpaArticleMapper {
    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "brand", source = "brand")
    Article toDomain(ArticleEntity articleEntity);

    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "brand", source = "brand")
    ArticleEntity toEntity(Article article);
}
