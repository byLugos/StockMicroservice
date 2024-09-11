package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.spi.CategoryOut;
import com.microservicio.stock.domain.pageable.PageCustom;
import com.microservicio.stock.domain.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryOut categoryOut;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryOut = mock(CategoryOut.class);
        categoryService = new CategoryService(categoryOut);
    }

    @Test
    void testCreateCategory_Success() {
        when(categoryOut.existByName("New Category")).thenReturn(false);
        Category categoryToSave = new Category(null, "New Category", "New Description");
        when(categoryOut.save(any(Category.class))).thenReturn(categoryToSave);

        Category result = categoryService.createCategory("New Category", "New Description");

        verify(categoryOut).existByName("New Category");
        verify(categoryOut).save(any(Category.class));

        assertNotNull(result);
        assertEquals("New Category", result.getName());
        assertEquals("New Description", result.getDescription());
    }

    @Test
    void testCreateCategory_InvalidNameException() {
        when(categoryOut.existByName("Existing Category")).thenReturn(true);

        InvalidNameExceptionMe exception = assertThrows(InvalidNameExceptionMe.class, () -> {
            categoryService.createCategory("Existing Category", "Description");
        });

        assertEquals("El nombre de la categoria ya existe", exception.getMessage());
        verify(categoryOut).existByName("Existing Category");
        verify(categoryOut, never()).save(any(Category.class));
    }

    @Test
    void testListCategory_Success() {
        List<Category> categories = Arrays.asList(
                new Category(1L, "Category1", "Description1"),
                new Category(2L, "Category2", "Description2")
        );
        when(categoryOut.findAll()).thenReturn(categories);

        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 2,true);

        PageCustom<Category> result = categoryService.listCategory(pageRequestCustom,"Category1","asc");

        verify(categoryOut).findAll();

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Category1", result.getContent().get(0).getName());
    }
}
