package com.microservicio.stock.domain.exception;

public class InvalidDescriptionException extends RuntimeException{
    public InvalidDescriptionException(String message) {
        super(message);
    }
}
