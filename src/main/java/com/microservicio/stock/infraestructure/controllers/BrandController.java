package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.handler.BrandHandler;
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
@RequestMapping("/brands")
@AllArgsConstructor
public class BrandController {

    private final BrandHandler brandHandler;

    @Operation(summary = "Crea una nueva marca", description = "Endpoint para crear una nueva marca en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<BrandDTO> createCategory(
            @Parameter(description = "Detalles de la marca a crear", required = true)
            @RequestBody BrandDTO brandDTO) {
        BrandDTO newBrand = brandHandler.createBrand(brandDTO);
        return new ResponseEntity<>(newBrand, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una lista de marcas", description = "Devuelve una lista paginada de todas las marcas disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de marcas obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de consulta inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<BrandDTO>> listBrands(
            @Parameter(description = "Parámetros de paginación y ordenación", example = "page=0&size=10&sort=name,asc")
            Pageable pageable) {
        Page<BrandDTO> brands = brandHandler.listBrands(pageable);
        return ResponseEntity.ok(brands);
    }
}
