package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.spi.CategoryOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryOut categoryOut;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory_Successful() {
        String nombre = "Electronics";
        String descripcion = "All electronic items";

        when(categoryOut.existByName(nombre)).thenReturn(false);
        when(categoryOut.save(any(Category.class))).thenReturn(new Category(1L, nombre, descripcion));

        Category result = categoryService.createCategory(nombre, descripcion);

        assertNotNull(result);
        assertEquals(nombre, result.getName());
        assertEquals(descripcion, result.getDescription());
    }

    @Test
    void testCreateCategory_NameAlreadyExists() {
        String nombre = "Electronics";
        String descripcion = "All electronic items";

        when(categoryOut.existByName(nombre)).thenReturn(true);

        InvalidNameExceptionMe exception = assertThrows(InvalidNameExceptionMe.class, () -> {
            categoryService.createCategory(nombre, descripcion);
        });

        assertEquals("El nombre de la categoria ya existe", exception.getMessage());
    }

}