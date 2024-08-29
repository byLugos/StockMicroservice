package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.handler.CategoryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryHandler categoryHandler;

    @Operation(summary = "Crea una nueva categoría", description = "Endpoint para crear una nueva categoría en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(
            @Parameter(description = "Detalles de la categoría a crear", required = true)
            @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO newCategory = categoryHandler.createCategory(categoryDTO);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una lista de categorías", description = "Devuelve una lista paginada de todas las categorías disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de consulta inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> listCategories(
            @Parameter(description = "Parámetros de paginación y ordenación", example = "page=0&size=10&sort=name,asc")
            Pageable pageable) {
        Page<CategoryDTO> categories = categoryHandler.listCategories(pageable);
        return ResponseEntity.ok(categories);
    }
}
