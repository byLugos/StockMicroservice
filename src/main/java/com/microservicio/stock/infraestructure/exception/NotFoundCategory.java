package com.microservicio.stock.infraestructure.exception;

public class NotFoundCategory extends RuntimeException{
    public NotFoundCategory(String message) {
        super(message);
    }
}
