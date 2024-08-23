package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.mapper.ArticleMapper;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.ports.api.ArticleIn;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleHandler {

    private final ArticleIn articleIn;
    private final ArticleMapper articleMapper;

    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        // Convertir DTO a entidad de dominio
        Article article = articleMapper.toEntity(articleDTO);

        // Crear el artículo utilizando el puerto de entrada
        Article newArticle = articleIn.createArticle(
                article.getName(),
                article.getDescription(),
                article.getQuantity(),
                article.getPrice(),
                articleDTO.getCategoryIds()
        );

        // Convertir la entidad de dominio a DTO y retornarla
        return articleMapper.toDTO(newArticle);
    }
}
