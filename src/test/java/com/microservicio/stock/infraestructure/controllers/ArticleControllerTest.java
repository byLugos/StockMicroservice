package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.handler.ArticleHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ArticleControllerTest {

    private ArticleHandler articleHandler;
    private ArticleController articleController;

    @BeforeEach
    void setUp() {
        articleHandler = mock(ArticleHandler.class);
        articleController = new ArticleController(articleHandler);
    }

    @Test
    void testCreateArticle_Success() {
        // Configura el DTO de entrada y el resultado esperado
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName("New Article");
        articleDTO.setDescription("Description");
        articleDTO.setQuantity(10);
        articleDTO.setPrice(BigDecimal.valueOf(100));

        ArticleDTO savedArticleDTO = new ArticleDTO();
        savedArticleDTO.setId(1L);
        savedArticleDTO.setName("New Article");
        savedArticleDTO.setDescription("Description");
        savedArticleDTO.setQuantity(10);
        savedArticleDTO.setPrice(BigDecimal.valueOf(100));

        // Configura el comportamiento del handler
        when(articleHandler.createArticle(articleDTO)).thenReturn(savedArticleDTO);

        // Llama al método a probar
        ResponseEntity<ArticleDTO> response = articleController.createArticle(articleDTO);

        // Verifica que el handler fue llamado correctamente
        verify(articleHandler).createArticle(articleDTO);

        // Verifica los resultados
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedArticleDTO, response.getBody());
    }

    @Test
    void testListArticles_Success() {
        // Configura una lista simulada de artículos
        ArticleDTO articleDTO1 = new ArticleDTO();
        articleDTO1.setId(1L);
        articleDTO1.setName("Article1");
        articleDTO1.setDescription("Description1");
        articleDTO1.setQuantity(5);
        articleDTO1.setPrice(BigDecimal.valueOf(50));

        ArticleDTO articleDTO2 = new ArticleDTO();
        articleDTO2.setId(2L);
        articleDTO2.setName("Article2");
        articleDTO2.setDescription("Description2");
        articleDTO2.setQuantity(3);
        articleDTO2.setPrice(BigDecimal.valueOf(30));

        List<ArticleDTO> articles = Arrays.asList(articleDTO1, articleDTO2);
        Page<ArticleDTO> articlePage = new PageImpl<>(articles);

        // Configura el comportamiento del handler
        Pageable pageable = PageRequest.of(0, 2);
        when(articleHandler.listArticles(pageable)).thenReturn(articlePage);

        // Llama al método a probar
        ResponseEntity<Page<ArticleDTO>> response = articleController.listArticles(pageable);

        // Verifica que el handler fue llamado correctamente
        verify(articleHandler).listArticles(pageable);

        // Verifica los resultados
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(articlePage, response.getBody());
    }
}
