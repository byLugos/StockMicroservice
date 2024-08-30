package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.handler.BrandHandler;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
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
    @Operation(
            summary = "Obtiene una lista de marcas",
            description = "Devuelve una lista paginada de todas las marcas disponibles, con opciones de filtrado y ordenamiento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de marcas obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de consulta inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<BrandDTO>> listCategories(
            @Parameter(description = "Nombre de la marca para filtrar (opcional)", example = "Adidas")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "Campo por el cual ordenar los resultados (por defecto: 'name')", example = "name")
            @RequestParam(value = "sort", defaultValue = "name") String sort,

            @Parameter(description = "Dirección del ordenamiento: 'asc' para ascendente o 'desc' para descendente (por defecto: 'asc')", example = "asc")
            @RequestParam(value = "direction", defaultValue = "asc") String direction,

            @Parameter(description = "Información de paginación (página y tamaño)", example = "page=0&size=10")
            Pageable pageable) {

        // Crear un nuevo PageRequestCustom con la dirección de ordenamiento obtenida de los parámetros
        PageRequestCustom pageRequestCustom = new PageRequestCustom(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                "asc".equalsIgnoreCase(direction)
        );

        // Obtener las categorías filtradas y ordenadas
        Page<BrandDTO> brands = brandHandler.listBrands(pageRequestCustom, name, sort);

        return ResponseEntity.ok(brands);
    }
}
