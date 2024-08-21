package com.microservicio.stock.domain.exception;

public class InvalidNameExceptionMe extends RuntimeException{
    public InvalidNameExceptionMe(String message) {
        super(message);
    }

}
