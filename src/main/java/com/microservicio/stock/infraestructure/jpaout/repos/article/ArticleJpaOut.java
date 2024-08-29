package com.microservicio.stock.infraestructure.jpaout.repos.article;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.ArticleCategory;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.spi.ArticleOut;
import com.microservicio.stock.infraestructure.exception.NotFoundCategory;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleCategoryEntity;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleEntity;
import com.microservicio.stock.infraestructure.jpaout.mapper.JpaArticleCategoryMapper;
import com.microservicio.stock.infraestructure.jpaout.mapper.JpaArticleMapper;
import com.microservicio.stock.infraestructure.jpaout.mapper.JpaCategoryMapper;
import com.microservicio.stock.infraestructure.jpaout.repos.category.CategoryRepository;
import com.microservicio.stock.infraestructure.jpaout.repos.relation.ArticleCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ArticleJpaOut implements ArticleOut {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleCategoryRepository articleCategoryRepository;
    private final JpaArticleMapper jpaArticleMapper;
    private final JpaCategoryMapper jpaCategoryMapper;
    private final JpaArticleCategoryMapper jpaArticleCategoryMapper;
    @Override
    public Article save(Article article) {
        ArticleEntity articleEntity = jpaArticleMapper.toEntity(article);
        ArticleEntity savedEntity = articleRepository.save(articleEntity);
        return jpaArticleMapper.toDomain(savedEntity);
    }
    @Override
    public void saveArticleCategory(ArticleCategory articleCategory) {
        ArticleCategoryEntity entity = jpaArticleCategoryMapper.toEntity(articleCategory);
        ArticleCategoryEntity savedEntity = articleCategoryRepository.save(entity);
        jpaArticleCategoryMapper.toDomain(savedEntity);
    }
    @Override
    public boolean existByName(String name) {
        return articleRepository.existsByName(name);
    }
    @Override
    public List<Article> findAll() {
        List<Object[]> results = articleCategoryRepository.findAllArticleDetailsWithCategories();
        Map<Long, Article> articlesMap = new HashMap<>();
        for (Object[] result : results) {
            Long articleId = (Long) result[0];
            String articleName = (String) result[1];
            String articleDescription = (String) result[2];
            int articleQuantity = (int) result[3];
            BigDecimal articlePrice = (BigDecimal) result[4];
            Long categoryId = (Long) result[5];
            String categoryName = (String) result[6];
            Article article = articlesMap.computeIfAbsent(articleId, id ->
                    new Article(id, articleName, articleDescription, articleQuantity, articlePrice, new ArrayList<>()));
            if (categoryId != null && categoryName != null) {
                Category category = new Category(categoryId, categoryName, null);
                ArticleCategory articleCategory = new ArticleCategory(null, article, category);
                article.getCategories().add(articleCategory);
            }
        }
        return new ArrayList<>(articlesMap.values());
    }
    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(jpaCategoryMapper::toDomain)
                .orElseThrow(()-> new NotFoundCategory("Categoria no encontrada"));
    }
}
