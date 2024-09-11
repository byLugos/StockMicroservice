package com.microservicio.stock.infraestructure.controllers;
import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.handler.ArticleHandler;
import com.microservicio.stock.domain.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        ArticleDTO articleDTO = new ArticleDTO(null, "Laptop", "High-end gaming laptop", 10, new BigDecimal("1500.00"), List.of(1L, 2L), List.of("Electronics", "Computers"), 1L, "BrandA");
        ArticleDTO savedArticleDTO = new ArticleDTO(1L, "Laptop", "High-end gaming laptop", 10, new BigDecimal("1500.00"), List.of(1L, 2L), List.of("Electronics", "Computers"), 1L, "BrandA");

        // Configuración de los mocks
        when(articleHandler.createArticle(any(ArticleDTO.class))).thenReturn(savedArticleDTO);

        // Llamada al método a probar
        ResponseEntity<ArticleDTO> response = articleController.createArticle(articleDTO);

        // Verificaciones
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedArticleDTO, response.getBody());
        verify(articleHandler).createArticle(articleDTO);
    }

    @Test
    void testListArticles() {
        // Datos de prueba
        ArticleDTO articleDTO = new ArticleDTO(1L, "Laptop", "High-end gaming laptop", 10, new BigDecimal("1500.00"), List.of(1L, 2L), List.of("Electronics", "Computers"), 1L, "BrandA");
        Page<ArticleDTO> pageResult = new PageImpl<>(List.of(articleDTO));
        Pageable pageable = PageRequest.of(0, 10);

        // Configuración de los mocks
        when(articleHandler.listArticles(any(PageRequestCustom.class), anyString(), anyString(), anyList(), anyString())).thenReturn(pageResult);

        // Llamada al método a probar
        ResponseEntity<Page<ArticleDTO>> response = articleController.listArticles("Laptop", "name", "asc", List.of("Electronics"), "BrandA", pageable);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getTotalElements());
        assertEquals(articleDTO, response.getBody().getContent().get(0));
        verify(articleHandler).listArticles(any(PageRequestCustom.class), eq("Laptop"), eq("name"), eq(List.of("Electronics")), eq("BrandA"));
    }
}
