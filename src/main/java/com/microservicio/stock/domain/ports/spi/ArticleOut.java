package com.microservicio.stock.domain.ports.spi;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.ArticleCategory;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.model.Category;
import java.util.List;
public interface ArticleOut {
    Article save(Article article);
    void saveArticleCategory(ArticleCategory articleCategory);
    boolean existByName(String name);
    List<Article> findAll();
    Category findCategoryById(Long id);
    Brand findBrandById(Long id);
    Article findById(Long id);
}
