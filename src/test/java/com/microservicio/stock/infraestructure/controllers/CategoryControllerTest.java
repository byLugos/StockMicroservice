package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.handler.CategoryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    private CategoryHandler categoryHandler;
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryHandler = mock(CategoryHandler.class);
        categoryController = new CategoryController(categoryHandler);
    }

    @Test
    void testCreateCategory_Success() {
        // Configura el DTO de entrada y el resultado esperado
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("New Category");
        categoryDTO.setDescription("Description");

        CategoryDTO savedCategoryDTO = new CategoryDTO();
        savedCategoryDTO.setId(1L);
        savedCategoryDTO.setName("New Category");
        savedCategoryDTO.setDescription("Description");

        // Configura el comportamiento del handler
        when(categoryHandler.createCategory(categoryDTO)).thenReturn(savedCategoryDTO);

        // Llama al método a probar
        ResponseEntity<CategoryDTO> response = categoryController.createCategory(categoryDTO);

        // Verifica que el handler fue llamado correctamente
        verify(categoryHandler).createCategory(categoryDTO);

        // Verifica los resultados
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedCategoryDTO, response.getBody());
    }

    @Test
    void testListCategories_Success() {
        // Configura una lista simulada de categorías
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        categoryDTO1.setName("Category1");
        categoryDTO1.setDescription("Description1");

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName("Category2");
        categoryDTO2.setDescription("Description2");

        List<CategoryDTO> categories = Arrays.asList(categoryDTO1, categoryDTO2);
        Page<CategoryDTO> categoryPage = new PageImpl<>(categories);

        // Configura el comportamiento del handler
        Pageable pageable = PageRequest.of(0, 2);
        when(categoryHandler.listCategories(pageable)).thenReturn(categoryPage);

        // Llama al método a probar
        ResponseEntity<Page<CategoryDTO>> response = categoryController.listCategories(pageable);

        // Verifica que el handler fue llamado correctamente
        verify(categoryHandler).listCategories(pageable);

        // Verifica los resultados
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryPage, response.getBody());
    }
}
