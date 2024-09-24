package com.microservicio.stock.domain.validations;

import com.microservicio.stock.domain.exception.InvalidDescriptionException;
import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;

public class BrandValidator {

    private BrandValidator() {
    }

    public static void validateName(String nombre) {
        if (nombre == null || nombre.isEmpty() || nombre.length() > 50) {
            throw new InvalidNameExceptionMe();
        }
    }

    public static void validateDescription(String descripcion) {
        if (descripcion == null || descripcion.isEmpty() || descripcion.length() > 120) {
            throw new InvalidDescriptionException();
        }
    }
}
