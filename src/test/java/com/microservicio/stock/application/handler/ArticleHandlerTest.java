package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.mapper.ArticleMapper;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.ports.api.ArticleIn;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleHandlerTest {

    private ArticleIn articleIn;
    private ArticleMapper articleMapper;
    private ArticleHandler articleHandler;

    @BeforeEach
    void setUp() {
        articleIn = mock(ArticleIn.class);
        articleMapper = mock(ArticleMapper.class);
        articleHandler = new ArticleHandler(articleIn, articleMapper);
    }

    @Test
    void testCreateArticle_Success() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName("New Article");
        articleDTO.setDescription("Description");
        articleDTO.setQuantity(10);
        articleDTO.setPrice(BigDecimal.valueOf(100));
        articleDTO.setCategories(Arrays.asList(1L, 2L));

        Article articleEntity = new Article(null, "New Article", "Description", 10, BigDecimal.valueOf(100), null);
        Article savedArticle = new Article(1L, "New Article", "Description", 10, BigDecimal.valueOf(100), null);

        when(articleMapper.toEntity(articleDTO)).thenReturn(articleEntity);
        when(articleIn.createArticle(anyString(), anyString(), anyInt(), any(BigDecimal.class), anyList())).thenReturn(savedArticle);
        when(articleMapper.toDTO(savedArticle)).thenReturn(articleDTO);

        ArticleDTO result = articleHandler.createArticle(articleDTO);

        verify(articleMapper).toEntity(articleDTO);
        verify(articleIn).createArticle(articleEntity.getName(), articleEntity.getDescription(), articleEntity.getQuantity(), articleEntity.getPrice(), articleDTO.getCategories());
        verify(articleMapper).toDTO(savedArticle);

        assertNotNull(result);
        assertEquals(articleDTO.getName(), result.getName());
        assertEquals(articleDTO.getDescription(), result.getDescription());
    }

    @Test
    void testListArticles_Success() {
        Article article1 = new Article(1L, "Article1", "Description1", 5, BigDecimal.valueOf(50), null);
        Article article2 = new Article(2L, "Article2", "Description2", 3, BigDecimal.valueOf(30), null);
        List<Article> articles = Arrays.asList(article1, article2);
        List<ArticleDTO> articleDTOs = Arrays.asList(
                new ArticleDTO(),
                new ArticleDTO()
        );

        articleDTOs.get(0).setId(1L);
        articleDTOs.get(0).setName("Article1");
        articleDTOs.get(0).setDescription("Description1");
        articleDTOs.get(0).setQuantity(5);
        articleDTOs.get(0).setPrice(BigDecimal.valueOf(50));
        articleDTOs.get(0).setCategories(List.of(1L));

        articleDTOs.get(1).setId(2L);
        articleDTOs.get(1).setName("Article2");
        articleDTOs.get(1).setDescription("Description2");
        articleDTOs.get(1).setQuantity(3);
        articleDTOs.get(1).setPrice(BigDecimal.valueOf(30));
        articleDTOs.get(1).setCategories(List.of(2L));

        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 2, true);
        PageCustom<Article> pageCustom = new PageCustom<>(
                articles,
                articles.size(),
                (int) Math.ceil((double) articles.size() / pageRequestCustom.getSize()),
                pageRequestCustom.getPage(),
                pageRequestCustom.isAscending()
        );
        when(articleIn.listArticle(any(PageRequestCustom.class))).thenReturn(pageCustom);
        when(articleMapper.toDTO(article1)).thenReturn(articleDTOs.get(0));
        when(articleMapper.toDTO(article2)).thenReturn(articleDTOs.get(1));

        Pageable pageable = PageRequest.of(0, 2);
        Page<ArticleDTO> result = articleHandler.listArticles(pageable);

        ArgumentCaptor<PageRequestCustom> pageRequestCaptor = ArgumentCaptor.forClass(PageRequestCustom.class);
        verify(articleIn).listArticle(pageRequestCaptor.capture());
        assertEquals(0, pageRequestCaptor.getValue().getPage());
        assertEquals(2, pageRequestCaptor.getValue().getSize());

        verify(articleMapper).toDTO(article1);
        verify(articleMapper).toDTO(article2);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(articleDTOs.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(articleDTOs.get(1).getName(), result.getContent().get(1).getName());
    }
}
