package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.handler.ArticleHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auxArticles")
@AllArgsConstructor
public class ArticleControllerAux {
    private final ArticleHandler articleHandler;
    @Operation(summary = "Actualiza la cantidad de stock de un artículo", description = "Endpoint para modificar la cantidad de stock de un artículo específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Artículo no encontrado"),
            @ApiResponse(responseCode = "400", description = "Cantidad inválida proporcionada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @GetMapping("/{articleId}/stock")
    public int currentStock(
            @Parameter(description = "ID del artículo", required = true)
            @PathVariable Long articleId) {
        return articleHandler.currentStock(articleId);
    }
    @GetMapping("/findByName")
    public ResponseEntity<Long> findArticleIdByName(@RequestParam String name) {
        Long articleId = articleHandler.findArticleIdByName(name);
        return ResponseEntity.ok(articleId);
    }

}
