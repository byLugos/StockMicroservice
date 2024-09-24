package com.microservicio.stock.domain.exception;

import com.microservicio.stock.utils.Constants;

public class InvalidStockException extends RuntimeException {
    public InvalidStockException() {
        super(Constants.STOCK_CANNOT_BE_NEGATIVE);
    }
}
