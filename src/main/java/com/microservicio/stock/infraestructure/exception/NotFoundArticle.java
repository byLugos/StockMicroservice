package com.microservicio.stock.infraestructure.exception;

import com.microservicio.stock.utils.Constants;

public class NotFoundArticle extends RuntimeException{
    public NotFoundArticle() {
        super(Constants.NOT_FOUND_ARTICLE);
    }
}
