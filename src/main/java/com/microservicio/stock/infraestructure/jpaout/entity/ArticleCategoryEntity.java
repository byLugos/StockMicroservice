package com.microservicio.stock.infraestructure.jpaout.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ArticleCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryEntity category;
}
