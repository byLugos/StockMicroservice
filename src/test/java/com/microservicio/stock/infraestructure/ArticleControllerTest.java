package com.microservicio.stock.infraestructure;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.handler.ArticleHandler;
import com.microservicio.stock.infraestructure.controllers.ArticleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ArticleControllerTest {

    @Mock
    private ArticleHandler articleHandler;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArticle() {
        // Datos de prueba
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName("Test Article");
        articleDTO.setDescription("Test Description");
        articleDTO.setQuantity(100);
        articleDTO.setPrice(new BigDecimal("99.99"));
        articleDTO.setCategoryIds(Arrays.asList(1L, 2L, 3L));

        ArticleDTO savedArticleDTO = new ArticleDTO();
        savedArticleDTO.setId(1L);
        savedArticleDTO.setName("Test Article");
        savedArticleDTO.setDescription("Test Description");
        savedArticleDTO.setQuantity(100);
        savedArticleDTO.setPrice(new BigDecimal("99.99"));
        savedArticleDTO.setCategoryIds(Arrays.asList(1L, 2L, 3L));

        // Configurar mockeo
        when(articleHandler.createArticle(any(ArticleDTO.class))).thenReturn(savedArticleDTO);

        // Llamar al método a probar
        ResponseEntity<ArticleDTO> response = articleController.createArticle(articleDTO);

        // Verificaciones
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedArticleDTO, response.getBody());
    }
}

