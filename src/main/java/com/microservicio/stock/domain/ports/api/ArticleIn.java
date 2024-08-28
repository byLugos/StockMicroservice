package com.microservicio.stock.domain.ports.api;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;

import java.math.BigDecimal;
import java.util.List;

public interface ArticleIn {
    Article createArticle(String name, String description, int quantity, BigDecimal price, List<Long> categoryIds);
    PageCustom<Article> listArticle(PageRequestCustom pageRequestCustom);

}
