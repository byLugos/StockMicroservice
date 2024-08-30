package com.microservicio.stock.domain.service;
import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.ports.spi.ArticleOut;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        // Datos de prueba
        String name = "Laptop";
        String description = "High-end gaming laptop";
        int quantity = 10;
        BigDecimal price = new BigDecimal("1500.00");
        List<Long> categoryIds = List.of(1L, 2L);
        Long brandId = 1L;

        Category category1 = new Category(1L, "Electronics", null);
        Category category2 = new Category(2L, "Computers", null);
        Brand brand = new Brand(1L, "BrandA", "Description");

        Article savedArticle = new Article(1L, name, description, quantity, price, new ArrayList<>(), brand);

        // Configuración de los mocks
        when(articleOut.existByName(name)).thenReturn(false);
        when(articleOut.findCategoryById(1L)).thenReturn(category1);
        when(articleOut.findCategoryById(2L)).thenReturn(category2);
        when(articleOut.findBrandById(brandId)).thenReturn(brand);
        when(articleOut.save(any(Article.class))).thenReturn(savedArticle);

        // Llamada al método a probar
        Article result = articleService.createArticle(name, description, quantity, price, categoryIds, brandId);

        // Verificaciones
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
        assertEquals(quantity, result.getQuantity());
        assertEquals(price, result.getPrice());
        assertEquals(brandId, result.getBrand().getId());

        verify(articleOut).existByName(name);
        verify(articleOut, times(2)).findCategoryById(anyLong());
        verify(articleOut).findBrandById(brandId);
        verify(articleOut).save(any(Article.class));
    }

    @Test
    void testCreateArticle_ExistingNameException() {
        // Datos de prueba
        String name = "Laptop";
        when(articleOut.existByName(name)).thenReturn(true);

        // Verificación de excepción
        InvalidNameExceptionMe exception = assertThrows(InvalidNameExceptionMe.class, () -> {
            articleService.createArticle(name, "Description", 10, new BigDecimal("1500.00"), List.of(1L, 2L), 1L);
        });

        assertEquals("El nombre del articulo ya existe", exception.getMessage());
        verify(articleOut).existByName(name);
    }
    @Test
    void testListArticles() {
        // Datos de prueba
        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 10, true);
        List<Article> articles = List.of(
                new Article(1L, "Laptop", "High-end gaming laptop", 10, new BigDecimal("1500.00"), new ArrayList<>(), new Brand(1L, "BrandA", "Description"))
        );
        // Configuración de los mocks
        when(articleOut.findAll()).thenReturn(articles);
        // Llamada al método a probar
        PageCustom<Article> result = articleService.listArticle(pageRequestCustom, "Laptop", "name", List.of("Electronics"), "BrandA");
        // Verificaciones
        assertEquals(0, result.getTotalElements());

        verify(articleOut).findAll();
    }
}
