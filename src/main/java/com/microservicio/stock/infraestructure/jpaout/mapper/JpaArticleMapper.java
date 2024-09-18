package com.microservicio.stock.infraestructure.jpaout.mapper;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.ArticleCategory;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleCategoryEntity;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleEntity;
import com.microservicio.stock.infraestructure.jpaout.entity.BrandEntity;
import com.microservicio.stock.infraestructure.jpaout.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JpaArticleMapper {
    public Article toDomain(ArticleEntity articleEntity) {
        if (articleEntity == null) {
            return null;
        }
        Brand brand = null;
        if (articleEntity.getBrand() != null) {
            brand = new Brand(
                    articleEntity.getBrand().getId(),
                    articleEntity.getBrand().getName(),
                    null
            );
        }
        List<ArticleCategory> categories = new ArrayList<>();
        if (articleEntity.getCategories() != null) {
            for (ArticleCategoryEntity categoryEntity : articleEntity.getCategories()) {
                Category category = new Category(
                        categoryEntity.getCategory().getId(),
                        categoryEntity.getCategory().getName(),
                        null
                );
                ArticleCategory articleCategory = new ArticleCategory(
                        null,
                        null,
                        category
                );
                categories.add(articleCategory);
            }
        }
        return new Article(
                articleEntity.getId(),
                articleEntity.getName(),
                articleEntity.getDescription(),
                articleEntity.getPrice(),
                categories,
                brand,
                articleEntity.getQuantity()
        );
    }
    public ArticleEntity toEntity(Article article) {
        if (article == null) {
            return null;
        }
        BrandEntity brandEntity = null;
        if (article.getBrand() != null) {
            brandEntity = new BrandEntity();
            brandEntity.setId(article.getBrand().getId());
            brandEntity.setName(article.getBrand().getName());
        }

        List<ArticleCategoryEntity> categories = new ArrayList<>();
        if (article.getCategories() != null) {
            for (ArticleCategory articleCategory : article.getCategories()) {
                ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setId(articleCategory.getCategory().getId());
                categoryEntity.setName(articleCategory.getCategory().getName());
                articleCategoryEntity.setArticle(null);
                articleCategoryEntity.setCategory(categoryEntity);
                categories.add(articleCategoryEntity);
            }
        }
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(article.getId());
        articleEntity.setName(article.getName());
        articleEntity.setDescription(article.getDescription());
        articleEntity.setPrice(article.getPrice());
        articleEntity.setCategories(categories);
        articleEntity.setBrand(brandEntity);
        articleEntity.setQuantity(article.getQuantity());
        for (ArticleCategoryEntity articleCategoryEntity : categories) {
            articleCategoryEntity.setArticle(articleEntity);
        }

        return articleEntity;
    }
}
