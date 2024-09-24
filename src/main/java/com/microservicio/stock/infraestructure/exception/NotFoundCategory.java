package com.microservicio.stock.infraestructure.exception;

import com.microservicio.stock.utils.Constants;

public class NotFoundCategory extends RuntimeException{
    public NotFoundCategory() {
        super(Constants.NOT_FOUND_CATEGORY);
    }
}
