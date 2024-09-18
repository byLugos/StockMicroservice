package com.microservicio.stock.infraestructure.handlerexcept;

import com.microservicio.stock.domain.exception.*;
import com.microservicio.stock.infraestructure.exception.ErrorResponse;
import com.microservicio.stock.infraestructure.exception.NotFoundCategory;
import com.microservicio.stock.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidNameExceptionMe.class)
    public ResponseEntity<ErrorResponse> handleInvalidNameException(InvalidNameExceptionMe ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Constants.INVALID_NAME);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDescriptionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDescriptionException(InvalidDescriptionException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Constants.INVALID_DESCRIPTION);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundCategory.class)
    public ResponseEntity<ErrorResponse> handlerNotFoundCategoryException(NotFoundCategory ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Constants.CATEGORY_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(Constants.GENERIC_ERROR_MESSAGE, Constants.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NotFoundArticle.class)
    public ResponseEntity<ErrorResponse> handleInvalidNameException(NotFoundArticle ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Constants.NOT_FOUND_ARTICLE);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidStockException.class)
    public ResponseEntity<ErrorResponse> handleInvalidNameException(InvalidStockException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Constants.STOCK_NEGATIVE);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}