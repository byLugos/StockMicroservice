package com.microservicio.stock.domain.exception;

import com.microservicio.stock.utils.Constants;

public class InvalidDescriptionException extends RuntimeException{
    public InvalidDescriptionException() {
        super(Constants.INVALID_DESCRIPTION);
    }
}
