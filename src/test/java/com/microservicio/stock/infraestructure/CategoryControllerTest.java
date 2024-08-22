package com.microservicio.stock.infraestructure;


import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.handler.CategoryHandler;
import com.microservicio.stock.infraestructure.controllers.CategoryController;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testListCategories_Success() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Books", "All kinds of books");
        Page<CategoryDTO> page = new PageImpl<>(List.of(categoryDTO));

        when(categoryHandler.listCategories(any(Pageable.class))).thenReturn(page);

        ResponseEntity<Page<CategoryDTO>> response = categoryController.listCategories(Pageable.unpaged());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1, response.getBody().getTotalElements());
    }
}