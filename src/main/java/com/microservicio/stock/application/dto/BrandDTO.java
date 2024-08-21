package com.microservicio.stock.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class BrandDTO {
    private Long id;
    private String nombre;
    private String descripcion;
}