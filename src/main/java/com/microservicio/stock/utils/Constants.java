package com.microservicio.stock.utils;

public class Constants {


    public static final String STOCK_CANNOT_BE_NEGATIVE = "El stock no puede ser negativo";

    private Constants(){

    }
    public static final String NAME = "name";
    public static final String UTILITY_CLASS = "Esta es una clase de utilidad, no se puede instanciar";
    public static final String NOT_FOUND_BRAND = "Marca no encontrada";
    public static final String NOT_FOUND_CATEGORY = "Marca no encontrada";
    public static final String INVALID_JWT_TOKEN = "Token JWT no válido: ";
    public static final String INVALID_NAME = "NOMBRE_INVALIDO";
    public static final String INVALID_DESCRIPTION = "DESCRIPCION_INVALIDA";
    public static final String CATEGORY_NOT_FOUND = "LA CATEGORIA NO SE PUDO ENCONTRAR";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String GENERIC_ERROR_MESSAGE = "Ha ocurrido un error interno. Por favor, inténtalo de nuevo más tarde.";
    public static final String NOT_FOUND_ARTICLE = "Articulo no encontrado";
    public static final String STOCK_NEGATIVE = "El stock del artículo actualmente está vacío";
    public static final String NO_AUTHORIZE = "Acceso denegado: No tienes permisos para acceder a este recurso.";

}
