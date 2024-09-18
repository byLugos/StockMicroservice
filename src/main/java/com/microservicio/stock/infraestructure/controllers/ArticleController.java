package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.handler.ArticleHandler;
import com.microservicio.stock.domain.pageable.PageRequestCustom;
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

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(
            @Parameter(description = "Detalles del artículo a crear", required = true)
            @RequestBody ArticleDTO articleDTO) {
        ArticleDTO newArticle = articleHandler.createArticle(articleDTO);
        return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una lista de artículos", description = "Devuelve una lista paginada de todos los artículos disponibles, con opciones de filtrado y ordenamiento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de artículos obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de consulta inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ArticleDTO>> listArticles(
            @Parameter(description = "Nombre del artículo para filtrar (opcional)", example = "Laptop")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "Campo por el cual ordenar los resultados (por defecto: 'name')", example = "name")
            @RequestParam(value = "sort", defaultValue = "name") String sort,

            @Parameter(description = "Dirección del ordenamiento: 'asc' para ascendente o 'desc' para descendente (por defecto: 'asc')", example = "asc")
            @RequestParam(value = "direction", defaultValue = "asc") String direction,

            @Parameter(description = "Lista de nombres de categorías para filtrar (opcional)", example = "Electronics,Books")
            @RequestParam(value = "categoryNames", required = false) List<String> categoryNames,

            @Parameter(description = "Nombre de la marca para filtrar (opcional)", example = "Apple")
            @RequestParam(value = "brandName", required = false) String brandName,

            @Parameter(description = "Información de paginación (página y tamaño)", example = "page=0&size=10")
            Pageable pageable) {
        PageRequestCustom pageRequestCustom = new PageRequestCustom(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                "asc".equalsIgnoreCase(direction)
        );
        Page<ArticleDTO> articles = articleHandler.listArticles(pageRequestCustom, name, sort, categoryNames, brandName);

        return ResponseEntity.ok(articles);
    }

    @Operation(summary = "Actualiza la cantidad de stock de un artículo", description = "Endpoint para modificar la cantidad de stock de un artículo específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Artículo no encontrado"),
            @ApiResponse(responseCode = "400", description = "Cantidad inválida proporcionada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('AUXBODEGA')")
    @PutMapping("/{articleId}/quantity")
    public ResponseEntity<ArticleDTO> updateStock(
            @Parameter(description = "ID del artículo a actualizar", required = true)
            @PathVariable Long articleId,

            @Parameter(description = "Cantidad a agregar o restar del stock", required = true)
            @RequestParam int quantity) {
        ArticleDTO updatedArticle = articleHandler.updateStock(articleId, quantity);
        return ResponseEntity.ok(updatedArticle);
    }

    @Operation(summary = "Obtiene el stock actual de un artículo", description = "Devuelve la cantidad actual de stock para un artículo dado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Artículo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('AUXBODEGA')")
    @GetMapping("/{articleId}/stock")
    public int currentStock(
            @Parameter(description = "ID del artículo", required = true)
            @PathVariable Long articleId) {
        return articleHandler.currentStock(articleId);
    }
}
