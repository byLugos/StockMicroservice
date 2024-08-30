package com.microservicio.stock.infraestructure.jpaout.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ArticleCategoryEntity> categories;
    @ManyToOne(fetch = FetchType.LAZY)  // Relación de muchos a uno con BrandEntity
    @JoinColumn(name = "brand_id")  // Clave foránea para la marca
    private BrandEntity brand;
}
