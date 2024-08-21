package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.spi.CategoryOut;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    @Test
    void testListCategory_AscendingOrder() {
        List<Category> categories = Arrays.asList(
                new Category(1L, "Electronics", "All electronic items"),
                new Category(2L, "Books", "Various kinds of books"),
                new Category(3L, "Furniture", "Home and office furniture")
        );

        when(categoryOut.findAll()).thenReturn(categories);

        PageRequestCustom pageRequest = new PageRequestCustom(0, 2, true); // Orden ascendente, página 0, tamaño 2
        PageCustom<Category> result = categoryService.listCategory(pageRequest);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Books", result.getContent().get(0).getName()); // Verifica que el orden sea ascendente
        assertEquals("Electronics", result.getContent().get(1).getName());
        assertEquals(2, result.getTotalPages());
        assertEquals(0, result.getCurrentPage());
        assertTrue(result.isAscending()); // Verifica que el orden sea ascendente
        assertFalse(result.isEmpty()); // Verifica que la página no esté vacía
    }

    @Test
    void testListCategory_EmptyPage() {
        when(categoryOut.findAll()).thenReturn(Collections.emptyList());

        PageRequestCustom pageRequest = new PageRequestCustom(0, 2, true);
        PageCustom<Category> result = categoryService.listCategory(pageRequest);

        assertNotNull(result);
        assertTrue(result.isEmpty()); // Verifica que la página esté vacía
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages()); //
        assertEquals(0, result.getCurrentPage());
        assertTrue(result.isAscending()); // Verifica que el orden sea ascendente
    }

}