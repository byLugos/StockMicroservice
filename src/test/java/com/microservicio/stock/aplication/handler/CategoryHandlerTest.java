package com.microservicio.stock.aplication.handler;


import com.microservicio.stock.application.dto.CategoryDTO;
import com.microservicio.stock.application.handler.CategoryHandler;
import com.microservicio.stock.application.mapper.CategoryMapper;
import com.microservicio.stock.application.mapper.PageMapper;
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
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testListCategory_Success() {
        Category category = new Category(1L, "Books", "All kinds of books");
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Books", "All kinds of books");

        when(categoryIn.listCategory(any(PageRequestCustom.class))).thenReturn(new PageCustom<>(List.of(category), 1, 1, 0, true));
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(categoryDTO);

        Pageable pageable = PageMapper.toSpringPageable(new PageRequestCustom(0, 10, true));
        Page<CategoryDTO> result = categoryHandler.listCategories(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(categoryDTO, result.getContent().get(0));
    }
}