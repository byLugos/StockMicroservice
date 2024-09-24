package com.microservicio.stock.domain.validations;

import com.microservicio.stock.domain.exception.InvalidDescriptionException;
import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;

public class CategoryValidator {

    private CategoryValidator() {
    }
    public static void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > 50) {
            throw new InvalidNameExceptionMe();
        }
    }

    public static void validateDescription(String description) {
        if (description == null || description.isEmpty() || description.length() > 120) {
            throw new InvalidDescriptionException();
        }
    }

}
