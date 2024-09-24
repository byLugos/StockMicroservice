package com.microservicio.stock.domain.exception;

import com.microservicio.stock.utils.Constants;

public class InvalidNameExceptionMe extends RuntimeException{
    public InvalidNameExceptionMe() {
        super(Constants.INVALID_NAME);
    }
}
