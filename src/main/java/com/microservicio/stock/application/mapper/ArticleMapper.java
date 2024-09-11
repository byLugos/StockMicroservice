package com.microservicio.stock.application.mapper;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.ArticleCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Collections;
import java.util.List;
@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(target = "categories", ignore = true)
    Article toEntity(ArticleDTO articleDTO);

    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategoriesToIds")
    @Mapping(target = "categoryNames", source = "categories", qualifiedByName = "mapCategoriesToNames")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.name")
    ArticleDTO toDTO(Article article);

    @Named("mapCategoriesToIds")
    default List<Long> mapCategoriesToIds(List<ArticleCategory> articleCategories) {
        if (articleCategories == null) {
            return Collections.emptyList();
        }
        return articleCategories.stream()
                .map(articleCategory -> articleCategory.getCategory().getId())
                .distinct()
                .toList();
    }

    @Named("mapCategoriesToNames")
    default List<String> mapCategoriesToNames(List<ArticleCategory> articleCategories) {
        if (articleCategories == null) {
            return Collections.emptyList();
        }
        return articleCategories.stream()
                .map(articleCategory -> articleCategory.getCategory().getName())
                .distinct()
                .toList();
    }
}

