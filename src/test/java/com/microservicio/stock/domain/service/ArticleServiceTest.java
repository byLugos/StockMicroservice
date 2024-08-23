package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.ArticleCategory;
import com.microservicio.stock.domain.ports.spi.ArticleOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ArticleServiceTest {

    @Mock
    private ArticleOut articleOut;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArticle_Success() {
        String name = "Electronics";
        String description = "All electronic items";
        int quantity = 1;
        BigDecimal price= BigDecimal.valueOf(12000);
        List<ArticleCategory> categories = new ArrayList<>();
        Long [] ids = {1L, 2L};
        when(articleOut.existByName(name)).thenReturn(false);
        when(articleOut.save(any(Article.class))).thenReturn(new Article(1L, name, description,quantity,price,categories));

        Article result = articleService.createArticle(name,description,quantity,price, List.of(ids));

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
    }

    @Test
    void testCreateCategory_NameAlreadyExists() {
        String name = "Electronics";
        String description = "All electronic items";
        int quantity = 1;
        BigDecimal price= BigDecimal.valueOf(12000);
        Long [] ids = {1L, 2L};
        List<Long> categoryIds = List.of(ids);
        when(articleOut.existByName(name)).thenReturn(true);

        InvalidNameExceptionMe exception = assertThrows(
                InvalidNameExceptionMe.class,
                () -> articleService.createArticle(name, description, quantity, price, categoryIds)
        );

        assertEquals("El nombre del articulo ya existe", exception.getMessage());
    }
}
