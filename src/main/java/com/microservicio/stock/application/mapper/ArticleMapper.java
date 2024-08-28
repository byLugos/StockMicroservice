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
    @Mapping(target = "categories", ignore = true)  // Ignorar el mapeo directo de categorías
    Article toEntity(ArticleDTO articleDTO);
    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategoriesToIds")
    ArticleDTO toDTO(Article article);
    @Named("mapCategoriesToIds")
    default List<Long> mapCategoriesToIds(List<ArticleCategory> categories) {
        if (categories == null) {
            return Collections.emptyList();
        }
        return categories.stream()
                .map(articleCategory -> articleCategory.getCategory().getId())
                .toList();
    }
}