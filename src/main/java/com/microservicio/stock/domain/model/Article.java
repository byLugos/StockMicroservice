package com.microservicio.stock.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Article {

    private Long id;

    private String name;

    private String description;

    private int quantity;

    private BigDecimal price;

    private List<ArticleCategory> categories;
    //lista de categorias


    public Article(Long id, String name, String description, int quantity, BigDecimal price, List<ArticleCategory> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.categories = categories != null ? categories : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<ArticleCategory> getCategories() {
        return categories;
    }
}
