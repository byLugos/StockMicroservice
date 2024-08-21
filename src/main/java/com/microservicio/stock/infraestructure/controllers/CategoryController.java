package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.handler.CategotyHandler;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategotyHandler categoryHandler;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO newCategory = categoryHandler.createCategory(categoryDTO);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<PageCustom<CategoryDTO>> listCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        PageRequestCustom pageRequestCustom = new PageRequestCustom(page, size, ascending);
        PageCustom<CategoryDTO> categories = categoryHandler.listCategories(pageRequestCustom);
        return ResponseEntity.ok(categories);
    }
}