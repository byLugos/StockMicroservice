package com.microservicio.stock.domain.exception;

public class InvalidStockException extends RuntimeException {
    public InvalidStockException(String message) {
        super(message);
    }
}
