package com.microservicio.stock.aplication.handler;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.handler.ArticleHandler;
import com.microservicio.stock.application.mapper.ArticleMapper;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.ports.api.ArticleIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArticleHandlerTest {

    @Mock
    private ArticleIn articleIn;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleHandler articleHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArticle() {
        // Datos de prueba
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName("Test Article");
        articleDTO.setDescription("Test Description");
        articleDTO.setQuantity(100);
        articleDTO.setPrice(new BigDecimal("99.99"));
        articleDTO.setCategoryIds(categoryIds);

        Article article = new Article(null, "Test Article", "Test Description", 100, new BigDecimal("99.99"), null);
        Article savedArticle = new Article(1L, "Test Article", "Test Description", 100, new BigDecimal("99.99"), null);

        ArticleDTO expectedDTO = new ArticleDTO();
        expectedDTO.setId(1L);
        expectedDTO.setName("Test Article");
        expectedDTO.setDescription("Test Description");
        expectedDTO.setQuantity(100);
        expectedDTO.setPrice(new BigDecimal("99.99"));
        expectedDTO.setCategoryIds(categoryIds);

        // Configurar mockeo
        when(articleMapper.toEntity(articleDTO)).thenReturn(article);
        when(articleIn.createArticle(
                article.getName(),
                article.getDescription(),
                article.getQuantity(),
                article.getPrice(),
                articleDTO.getCategoryIds()
        )).thenReturn(savedArticle);
        when(articleMapper.toDTO(savedArticle)).thenReturn(expectedDTO);

        // Llamar al método a probar
        ArticleDTO result = articleHandler.createArticle(articleDTO);

        // Verificaciones
        assertEquals(expectedDTO, result);
        verify(articleMapper).toEntity(articleDTO);
        verify(articleIn).createArticle(
                article.getName(),
                article.getDescription(),
                article.getQuantity(),
                article.getPrice(),
                articleDTO.getCategoryIds()
        );
        verify(articleMapper).toDTO(savedArticle);
    }
}
