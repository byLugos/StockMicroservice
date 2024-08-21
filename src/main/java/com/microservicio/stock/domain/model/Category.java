package com.microservicio.stock.domain.model;

import lombok.Getter;

/**
 * Representa una categoría con un id, nombre y descripción.
 */
@Getter
public class Category {
    private final Long id;
    private final String name;
    private final String description;

    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
