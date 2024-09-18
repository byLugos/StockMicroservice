package com.microservicio.stock.domain.exception;

public class InvalidArticleQuantityException extends RuntimeException {
    public InvalidArticleQuantityException(String message) {
        super(message);
    }
}

