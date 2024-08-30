package com.microservicio.stock.domain.model;

public class ArticleCategory {
    private Long id;
    private Article article;
    private Category category;
    public ArticleCategory(Long id, Article article, Category category) {
        this.id = id;
        this.article = article;
        this.category = category;
    }
    public Long getId() {
        return id;
    }
    public Article getArticle() {
        return article;
    }
    public Category getCategory() {
        return category;
    }
}
