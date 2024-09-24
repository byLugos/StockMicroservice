package com.microservicio.stock.domain.exception;

import com.microservicio.stock.domain.util.Constants;

public class InvalidBrandExists extends RuntimeException {
    public InvalidBrandExists() {
        super(Constants.BRAND_EXISTS);
    }
}
