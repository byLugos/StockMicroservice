package com.microservicio.stock.domain.service;
import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.ArticleCategory;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.api.ArticleIn;
import com.microservicio.stock.domain.ports.spi.ArticleOut;
import com.microservicio.stock.domain.util.ArticleValidator;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import com.microservicio.stock.domain.util.pageable.PagingUtil;
import java.math.BigDecimal;
import java.util.List;
public class ArticleService implements ArticleIn {
    private final ArticleOut articleOut;
    public ArticleService(ArticleOut articleOut) {
        this.articleOut = articleOut;
    }
    @Override
    public Article createArticle(String name, String description, int quantity, BigDecimal price, List<Long> categoryIds, Long brandId) {
        ArticleValidator.validateName(name);
        ArticleValidator.validateDescription(description);
        if (articleOut.existByName(name)) {
            throw new InvalidNameExceptionMe("El nombre del articulo ya existe");
        }
        List<Category> categories = categoryIds.stream()
                .map(articleOut::findCategoryById)
                .distinct()
                .toList();

        if (categories.isEmpty() || categories.size() > 3) {
            throw new InvalidNameExceptionMe("El articulo debe tener entre 1 y 3 categorías.");
        }

        Brand brand = articleOut.findBrandById(brandId);
        if (brand == null) {
            throw new InvalidNameExceptionMe("La marca proporcionada no existe.");
        }

        Article newArticle = new Article(null, name, description, quantity, price, null, brand);
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
        // Filtrar por nombre de artículo si se proporciona
        if (name != null && !name.isEmpty()) {
            allArticles = allArticles.stream()
                    .filter(article -> article.getName().contains(name))
                    .toList();
        }
        // Filtrar por nombres de categoría si se proporcionan
        if (categoryNames != null && !categoryNames.isEmpty()) {
            allArticles = allArticles.stream()
                    .filter(article -> article.getCategories().stream()
                            .anyMatch(cat -> categoryNames.contains(cat.getCategory().getName())))
                    .toList();
        }
        // Filtrar por nombre de la marca si se proporciona
        if (brandName != null && !brandName.isEmpty()) {
            allArticles = allArticles.stream()
                    .filter(article -> article.getBrand() != null && article.getBrand().getName().contains(brandName))
                    .toList();
        }
        // Ordenar y paginar los elementos
        return PagingUtil.paginateAndSort(allArticles, pageRequestCustom, article -> {
            if ("name".equalsIgnoreCase(sort)) {
                return article.getName();
            }
            return article.getName(); // Default: Ordenar por nombre
        });
    }
}
