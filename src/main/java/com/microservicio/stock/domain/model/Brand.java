package com.microservicio.stock.domain.model;

import lombok.Getter;

/**
 * Representa una marca con un id, nombre y descripción.
 */
@Getter
public class Brand {
    private final Long id;
    private final String nombre;
    private final String descripcion;

    public Brand(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}