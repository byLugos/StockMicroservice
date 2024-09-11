package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.handler.CategoryHandler;
import com.microservicio.stock.domain.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CategoryControllerTest {

    @Mock
    private CategoryHandler categoryHandler;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory_Success() {
        // Datos de ejemplo
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Books", "All kinds of books");
        when(categoryHandler.createCategory(any(CategoryDTO.class))).thenReturn(categoryDTO);

        // Llamada al método y validación
        ResponseEntity<CategoryDTO> response = categoryController.createCategory(categoryDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryDTO, response.getBody());
    }

    @Test
    void testListCategories_Success() {
        // Datos de ejemplo
        CategoryDTO category1 = new CategoryDTO(1L, "Books", "All kinds of books");
        CategoryDTO category2 = new CategoryDTO(2L, "Electronics", "Devices and gadgets");
        List<CategoryDTO> categoryList = List.of(category1, category2);
        Page<CategoryDTO> page = new PageImpl<>(categoryList);

        // Mock del comportamiento
        when(categoryHandler.listCategories(any(PageRequestCustom.class), anyString(), anyString()))
                .thenReturn(page);

        // Parámetros de prueba
        String name = "Books";
        String sort = "name";
        String direction = "asc";
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        // Llamada al método y validación
        ResponseEntity<Page<CategoryDTO>> response = categoryController.listCategories(name, sort, direction, pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }

    @Test
    void testListCategories_InvalidParameters() {
        // Datos de ejemplo de respuesta vacía
        Page<CategoryDTO> emptyPage = Page.empty();
        when(categoryHandler.listCategories(any(PageRequestCustom.class), anyString(), anyString()))
                .thenReturn(emptyPage);

        // Parámetros de prueba con nombre no existente
        String name = "NonExistent";
        String sort = "name";
        String direction = "asc";
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        // Llamada al método y validación
        ResponseEntity<Page<CategoryDTO>> response = categoryController.listCategories(name, sort, direction, pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, Objects.requireNonNull(response.getBody()).getTotalElements());
    }
}
