package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.handler.ArticleHandler;
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
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {

    private final ArticleHandler articleHandler;

    @Operation(summary = "Crea un nuevo artículo", description = "Endpoint para crear un artículo en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artículo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(
            @Parameter(description = "Detalles del artículo a crear", required = true)
            @RequestBody ArticleDTO articleDTO) {
        ArticleDTO newArticle = articleHandler.createArticle(articleDTO);
        return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una lista de artículos", description = "Devuelve una lista paginada de todos los artículos disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de artículos obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de consulta inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<ArticleDTO>> listArticles(
            @Parameter(description = "Parámetros de paginación y ordenación", example = "page=0&size=10&sort=name,asc")
            Pageable pageable) {
        Page<ArticleDTO> articles = articleHandler.listArticles(pageable);
        return ResponseEntity.ok(articles);
    }
}
