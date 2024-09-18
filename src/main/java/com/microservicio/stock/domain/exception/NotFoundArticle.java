package com.microservicio.stock.domain.exception;

public class NotFoundArticle extends RuntimeException{
    public NotFoundArticle(String message) {
        super(message);
    }
}
