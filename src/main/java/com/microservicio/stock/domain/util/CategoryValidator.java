package com.microservicio.stock.domain.util;

import com.microservicio.stock.domain.exception.InvalidDescriptionException;
import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;

public class CategoryValidator {

    private static final String ERROR_INVALID_NAME = "El nombre de la categoria no puede ser nulo, vacío, o exceder 50 caracteres.";
    private static final String ERROR_INVALID_DESCRIPTION = "La descripción de la categoria no puede ser nula, vacía, o exceder 120 caracteres.";

    public static void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > 50) {
            throw new InvalidNameExceptionMe(ERROR_INVALID_NAME);
        }
    }

    public static void validateDescription(String description) {
        if (description == null || description.isEmpty() || description.length() > 120) {
            throw new InvalidDescriptionException(ERROR_INVALID_DESCRIPTION);
        }
    }

}
