package com.microservicio.stock.infraestructure.handlerexcept;

import com.microservicio.stock.domain.exception.InvalidDescriptionException;
import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.infraestructure.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidNameExceptionMe.class)
    public ResponseEntity<ErrorResponse> handleCategoriaNombreInvalidoException(InvalidNameExceptionMe ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "NOMBRE_INVALIDO");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDescriptionException.class)
    public ResponseEntity<ErrorResponse> handleCategoriaDescripcionInvalidaException(InvalidDescriptionException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "DESCRIPCION_INVALIDA");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("Ha ocurrido un error interno. Por favor, inténtalo de nuevo más tarde.", "INTERNAL_SERVER_ERROR");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}