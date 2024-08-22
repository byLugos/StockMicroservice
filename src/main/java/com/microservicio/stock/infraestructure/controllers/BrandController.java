package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.handler.BrandHandler;
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

    @PostMapping
    public ResponseEntity<BrandDTO> createCategory(@RequestBody BrandDTO brandDTO) {
        BrandDTO newBrand = brandHandler.createBrand(brandDTO);
        return new ResponseEntity<>(newBrand, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<BrandDTO>> listBrands(Pageable pageable) {
        // Llamar al handler para obtener la lista paginada y ordenada de categorías en forma de DTO
        Page<BrandDTO> brands = brandHandler.listBrands(pageable);
        return ResponseEntity.ok(brands);
    }
}