package com.microservicio.stock.domain.exception;

import com.microservicio.stock.domain.util.Constants;

public class InvalidCategoryExists extends RuntimeException{
    public InvalidCategoryExists() {
        super(Constants.CATEGORY_EXISTS);
    }
}
