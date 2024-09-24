package com.microservicio.stock.infraestructure.exception;

import com.microservicio.stock.utils.Constants;

public class NotFoundBrand extends RuntimeException{
    public NotFoundBrand() {
        super(Constants.NOT_FOUND_BRAND);
    }
}
