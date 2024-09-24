package com.microservicio.stock.domain.validations;

import com.microservicio.stock.domain.exception.InvalidDescriptionException;
import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.util.Constants;

public class ArticleValidator {
    private ArticleValidator() {
    }
    public static void validateName(String nombre) {
        if (nombre == null || nombre.isEmpty() || nombre.length() > Constants.FIFTHY) {
            throw new InvalidNameExceptionMe();
        }
    }

    public static void validateDescription(String descripcion) {
        if (descripcion == null || descripcion.isEmpty() || descripcion.length() > Constants.NUMBER) {
            throw new InvalidDescriptionException();
        }
    }
}