package com.microservicio.stock.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
}
