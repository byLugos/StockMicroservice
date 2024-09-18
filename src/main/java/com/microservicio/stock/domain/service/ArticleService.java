package com.microservicio.stock.domain.service;
import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.ArticleCategory;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.api.ArticleIn;
import com.microservicio.stock.domain.ports.spi.ArticleOut;
import com.microservicio.stock.domain.validations.ArticleValidator;
import com.microservicio.stock.domain.pageable.PageCustom;
import com.microservicio.stock.domain.pageable.PageRequestCustom;
import com.microservicio.stock.domain.pageable.PagingUtil;
import com.microservicio.stock.utils.Constants;

import java.math.BigDecimal;
import java.util.List;
public class ArticleService implements ArticleIn {
    private final ArticleOut articleOut;
    public ArticleService(ArticleOut articleOut) {
        this.articleOut = articleOut;
    }
    @Override
    public Article createArticle(String name, String description, BigDecimal price, List<Long> categoryIds, Long brandId) {
        ArticleValidator.validateName(name);
        ArticleValidator.validateDescription(description);
        if (articleOut.existByName(name)) {
            throw new InvalidNameExceptionMe(com.microservicio.stock.domain.util.Constants.ARTICLE_NAME_EXISTS);
        }
        List<Category> categories = categoryIds.stream()
                .map(articleOut::findCategoryById)
                .distinct()
                .toList();

        if (categories.isEmpty() || categories.size() > 3) {
            throw new InvalidNameExceptionMe(com.microservicio.stock.domain.util.Constants.ARTICLE_INVALID_CATEGORIES);
        }

        Brand brand = articleOut.findBrandById(brandId);
        if (brand == null) {
            throw new InvalidNameExceptionMe(com.microservicio.stock.domain.util.Constants.ARTICLE_INVALID_BRAND);
        }

        Article newArticle = new Article(null, name, description, price, null, brand);
        Article savedArticle = articleOut.save(newArticle);

        for (Category category : categories) {
            ArticleCategory articleCategory = new ArticleCategory(null, savedArticle, category);
            articleOut.saveArticleCategory(articleCategory);
        }
        return savedArticle;
    }
    @Override
    public PageCustom<Article> listArticle(PageRequestCustom pageRequestCustom, String name, String sort, List<String> categoryNames, String brandName) {
        List<Article> allArticles = articleOut.findAll();
        if (name != null && !name.isEmpty()) {
            allArticles = allArticles.stream()
                    .filter(article -> article.getName().contains(name))
                    .toList();
        }
        if (categoryNames != null && !categoryNames.isEmpty()) {
            allArticles = allArticles.stream()
                    .filter(article -> article.getCategories().stream()
                            .anyMatch(cat -> categoryNames.contains(cat.getCategory().getName())))
                    .toList();
        }
        if (brandName != null && !brandName.isEmpty()) {
            allArticles = allArticles.stream()
                    .filter(article -> article.getBrand() != null && article.getBrand().getName().contains(brandName))
                    .toList();
        }
        return PagingUtil.paginateAndSort(allArticles, pageRequestCustom, article -> {
            if (Constants.NAME.equalsIgnoreCase(sort)) {
                return article.getName();
            }
            return article.getName();
        });
    }
}
