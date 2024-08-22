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
    void testCreateCategory_Success() {
        String name = "Electronics";
        String description = "All electronic items";

        when(categoryOut.existByName(name)).thenReturn(false);
        when(categoryOut.save(any(Category.class))).thenReturn(new Category(1L, name, description));

        Category result = categoryService.createCategory(name, description);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
    }

    @Test
    void testCreateCategory_NameAlreadyExists() {
        String name = "Electronics";
        String description = "All electronic items";

        when(categoryOut.existByName(name)).thenReturn(true);

        InvalidNameExceptionMe exception = assertThrows(InvalidNameExceptionMe.class, () -> {
            categoryService.createCategory(name, description);
        });

        assertEquals("El nombre de la categoria ya existe", exception.getMessage());
    }

    @Test
    void testListCategory_Success() {
        List<Category> categories = List.of(new Category(1L, "Books", "All kinds of books"), new Category(2L, "Electronics", "All electronic items"));
        when(categoryOut.findAll()).thenReturn(categories);

        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 10, true);
        PageCustom<Category> result = categoryService.listCategory(pageRequestCustom);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void testListCategory_Empty() {
        when(categoryOut.findAll()).thenReturn(Collections.emptyList());

        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 10, true);
        PageCustom<Category> result = categoryService.listCategory(pageRequestCustom);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
    }
}
