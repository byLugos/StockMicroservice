package com.microservicio.stock.domain.ports.api;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.pageable.PageCustom;
import com.microservicio.stock.domain.pageable.PageRequestCustom;
import java.math.BigDecimal;
import java.util.List;
public interface ArticleIn {
    Article createArticle(String name, String description, BigDecimal price, List<Long> categoryIds, Long brandId, int quantity);
    PageCustom<Article> listArticle(PageRequestCustom pageRequestCustom, String name, String sort, List<String> categoryNames, String brandName);
    Article updateQuantity(Long articleId, int quantityChange);
    int currentStock(Long articleId);
    Article findArticleByName(String name);
}
