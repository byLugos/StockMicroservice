package com.microservicio.stock.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<Long> categories;
    private List<String> categoryNames;
    private Long brandId;
    private String brandName;
}
