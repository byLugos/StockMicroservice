package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.mapper.ArticleMapper;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.ports.api.ArticleIn;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
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
        ArticleDTO articleDTO = new ArticleDTO(null, "Laptop", "High-end gaming laptop", 10, new BigDecimal("1500.00"), List.of(1L, 2L), List.of("Electronics", "Computers"), 1L, "BrandA");
        Article article = new Article(null, "Laptop", "High-end gaming laptop", 10, new BigDecimal("1500.00"), null, null);
        Article savedArticle = new Article(1L, "Laptop", "High-end gaming laptop", 10, new BigDecimal("1500.00"), null, null);

        // Configuración de los mocks
        when(articleMapper.toEntity(articleDTO)).thenReturn(article);
        when(articleIn.createArticle(anyString(), anyString(), anyInt(), any(BigDecimal.class), anyList(), anyLong())).thenReturn(savedArticle);
        when(articleMapper.toDTO(savedArticle)).thenReturn(articleDTO);

        // Llamada al método a probar
        ArticleDTO result = articleHandler.createArticle(articleDTO);

        // Verificaciones
        assertEquals(articleDTO.getName(), result.getName());
        assertEquals(articleDTO.getDescription(), result.getDescription());
        assertEquals(articleDTO.getPrice(), result.getPrice());
        verify(articleMapper).toEntity(articleDTO);
        verify(articleIn).createArticle(article.getName(), article.getDescription(), article.getQuantity(), article.getPrice(), articleDTO.getCategories(), articleDTO.getBrandId());
        verify(articleMapper).toDTO(savedArticle);
    }

    @Test
    void testListArticles() {
        // Datos de prueba
        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 10, true);
        List<Article> articles = List.of(
                new Article(1L, "Laptop", "High-end gaming laptop", 10, new BigDecimal("1500.00"), null, null)
        );
        PageCustom<Article> pageCustom = new PageCustom<>(articles, 1, 1, 0, true);

        ArticleDTO articleDTO = new ArticleDTO(1L, "Laptop", "High-end gaming laptop", 10, new BigDecimal("1500.00"), List.of(1L, 2L), List.of("Electronics", "Computers"), 1L, "BrandA");

        // Configuración de los mocks
        when(articleIn.listArticle(any(PageRequestCustom.class), anyString(), anyString(), anyList(), anyString())).thenReturn(pageCustom);
        when(articleMapper.toDTO(any(Article.class))).thenReturn(articleDTO);

        // Llamada al método a probar
        Page<ArticleDTO> result = articleHandler.listArticles(pageRequestCustom, "Laptop", "name", List.of("Electronics"), "BrandA");

        // Verificaciones
        assertEquals(1, result.getTotalElements());
        assertEquals("Laptop", result.getContent().get(0).getName());
        verify(articleIn).listArticle(any(PageRequestCustom.class), eq("Laptop"), eq("name"), eq(List.of("Electronics")), eq("BrandA"));
        verify(articleMapper, times(articles.size())).toDTO(any(Article.class));
    }
}
