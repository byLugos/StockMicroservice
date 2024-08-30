package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.mapper.CategoryMapper;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.api.CategoryIn;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CategoryHandlerTest {

    @Mock
    private CategoryIn categoryIn;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory_Success() {
        // Datos de ejemplo
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Books", "All kinds of books");
        Category category = new Category(1L, "Books", "All kinds of books");
        when(categoryMapper.toEntity(any(CategoryDTO.class))).thenReturn(category);
        when(categoryIn.createCategory(anyString(), anyString())).thenReturn(category);
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(categoryDTO);

        // Llamada al método y validación
        CategoryDTO result = categoryHandler.createCategory(categoryDTO);
        assertEquals(categoryDTO, result);
    }

    @Test
    void testListCategories_Success() {
        // Datos de ejemplo
        Category category1 = new Category(1L, "Books", "All kinds of books");
        Category category2 = new Category(1L, "Electronics", "Devices and gadgets");
        List<Category> categories = List.of(category1, category2);

        PageCustom<Category> pageCustom = new PageCustom<>(categories, 2, 1, 0, true);
        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 10, true);

        when(categoryIn.listCategory(any(PageRequestCustom.class), anyString(), anyString())).thenReturn(pageCustom);

        CategoryDTO categoryDTO1 = new CategoryDTO(1L, "Books", "All kinds of books");
        CategoryDTO categoryDTO2 = new CategoryDTO(1L, "Electronics", "Devices and gadgets");

        when(categoryMapper.toDTO(category1)).thenReturn(categoryDTO1);
        when(categoryMapper.toDTO(category2)).thenReturn(categoryDTO2);

        // Llamada al método y validación
        Page<CategoryDTO> resultPage = categoryHandler.listCategories(pageRequestCustom, "Books", "name");
        assertEquals(2, resultPage.getTotalElements());
        assertEquals(1, resultPage.getTotalPages());
        assertEquals(List.of(categoryDTO1, categoryDTO2), resultPage.getContent());
    }
}
