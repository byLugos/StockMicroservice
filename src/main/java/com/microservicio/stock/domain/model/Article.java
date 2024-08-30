package com.microservicio.stock.domain.model;
import java.math.BigDecimal;
import java.util.List;
public class Article {
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    private List<ArticleCategory> categories;
    private Brand brand;
    public Article(Long id, String name, String description, int quantity, BigDecimal price, List<ArticleCategory> categories, Brand brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.categories = categories;
        this.brand = brand;
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
    public Brand getBrand() {
        return brand;
    }
}
