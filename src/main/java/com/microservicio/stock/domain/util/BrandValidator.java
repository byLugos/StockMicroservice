package com.microservicio.stock.domain.util;

import com.microservicio.stock.domain.exception.InvalidDescriptionException;
import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;

public class BrandValidator {

    private static final String ERROR_INVALID_NAME = "El nombre de la marca no puede ser nulo, vacío, o exceder 50 caracteres.";
    private static final String ERROR_INVALID_DESCRIPTION = "La descripción de la marca no puede ser nula, vacía, o exceder 120 caracteres.";

    public static void validateName(String nombre) {
        if (nombre == null || nombre.isEmpty() || nombre.length() > 50) {
            throw new InvalidNameExceptionMe(ERROR_INVALID_NAME);
        }
    }

    public static void validateDescription(String descripcion) {
        if (descripcion == null || descripcion.isEmpty() || descripcion.length() > 120) {
            throw new InvalidDescriptionException(ERROR_INVALID_DESCRIPTION);
        }
    }
}
