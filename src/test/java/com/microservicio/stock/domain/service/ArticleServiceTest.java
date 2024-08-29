package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.ArticleCategory;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.spi.ArticleOut;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServiceTest {

    private ArticleOut articleOut;
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        articleOut = mock(ArticleOut.class);
        articleService = new ArticleService(articleOut);
    }

    @Test
    void testCreateArticle_Success() {
        when(articleOut.existByName("New Article")).thenReturn(false);

        Category category1 = new Category(1L, "Category1", "Description1");
        Category category2 = new Category(2L, "Category2", "Description2");
        when(articleOut.findCategoryById(1L)).thenReturn(category1);
        when(articleOut.findCategoryById(2L)).thenReturn(category2);

        Article articleToSave = new Article(null, "New Article", "Description", 10, BigDecimal.valueOf(100), null);
        when(articleOut.save(any(Article.class))).thenReturn(articleToSave);

        Article result = articleService.createArticle("New Article", "Description", 10, BigDecimal.valueOf(100), Arrays.asList(1L, 2L));

        verify(articleOut).existByName("New Article");
        verify(articleOut).save(any(Article.class));
        verify(articleOut, times(2)).saveArticleCategory(any(ArticleCategory.class));

        assertNotNull(result);
        assertEquals("New Article", result.getName());
        assertEquals("Description", result.getDescription());
        assertEquals(10, result.getQuantity());
        assertEquals(BigDecimal.valueOf(100), result.getPrice());
    }

    @Test
    void testCreateArticle_InvalidNameException_ExistingName() {
        when(articleOut.existByName("Existing Article")).thenReturn(true);

        InvalidNameExceptionMe exception = assertThrows(InvalidNameExceptionMe.class, () -> articleService.createArticle("Existing Article", "Description", 10, BigDecimal.valueOf(100), List.of(1L)));

        assertEquals("El nombre del articulo ya existe", exception.getMessage());
        verify(articleOut).existByName("Existing Article");
        verify(articleOut, never()).save(any(Article.class));
    }

    @Test
    void testCreateArticle_InvalidNameException_CategorySize() {
        when(articleOut.existByName("New Article")).thenReturn(false);
        when(articleOut.findCategoryById(1L)).thenReturn(new Category(1L, "Category1", "Description1"));
        when(articleOut.findCategoryById(2L)).thenReturn(new Category(2L, "Category2", "Description2"));
        when(articleOut.findCategoryById(3L)).thenReturn(new Category(3L, "Category3", "Description3"));
        when(articleOut.findCategoryById(4L)).thenReturn(new Category(4L, "Category4", "Description4"));

        InvalidNameExceptionMe exception = assertThrows(InvalidNameExceptionMe.class, () -> articleService.createArticle("New Article", "Description", 10, BigDecimal.valueOf(100), Arrays.asList(1L, 2L, 3L, 4L)));

        assertEquals("El articulo debe tener entre 1 y 3 categorías.", exception.getMessage());
        verify(articleOut, never()).save(any(Article.class));
    }

    @Test
    void testListArticle_Success() {

        List<Article> articles = Arrays.asList(
                new Article(1L, "Article1", "Description1", 5, BigDecimal.valueOf(50), null),
                new Article(2L, "Article2", "Description2", 3, BigDecimal.valueOf(30), null)
        );
        when(articleOut.findAll()).thenReturn(articles);

        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 2, true);
        PageCustom<Article> result = articleService.listArticle(pageRequestCustom);

        verify(articleOut).findAll();

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Article1", result.getContent().get(0).getName());
        assertEquals("Article2", result.getContent().get(1).getName());
    }
}
