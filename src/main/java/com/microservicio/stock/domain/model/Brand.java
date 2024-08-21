package com.microservicio.stock.domain.model;

import lombok.Getter;

/**
 * Representa una marca con un id, nombre y descripción.
 */
@Getter
public class Brand {
    private final Long id;
    private final String name;
    private final String description;

    public Brand(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}