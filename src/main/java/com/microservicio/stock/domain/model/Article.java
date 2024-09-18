package com.microservicio.stock.domain.model;
import java.math.BigDecimal;
import java.util.List;
public class Article {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<ArticleCategory> categories;
    private Brand brand;
    private int quantity;

    public Article(Long id, String name, String description, BigDecimal price, List<ArticleCategory> categories, Brand brand, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = categories;
        this.brand = brand;
        this.quantity = quantity;
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

    public BigDecimal getPrice() {
        return price;
    }

    public List<ArticleCategory> getCategories() {
        return categories;
    }

    public Brand getBrand() {
        return brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
