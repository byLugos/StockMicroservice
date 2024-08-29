package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.mapper.ArticleMapper;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.ports.api.ArticleIn;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class ArticleHandler {
    private final ArticleIn articleIn;
    private final ArticleMapper articleMapper;
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        Article article = articleMapper.toEntity(articleDTO);
        Article newArticle = articleIn.createArticle(
                article.getName(),
                article.getDescription(),
                article.getQuantity(),
                article.getPrice(),
                articleDTO.getCategories()
        );
        return articleMapper.toDTO(newArticle);
    }
    public Page<ArticleDTO> listArticles(Pageable pageable) {
        PageRequestCustom pageRequestCustom = new PageRequestCustom(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().isSorted());
        PageCustom<Article> pageCustom = articleIn.listArticle(pageRequestCustom);
        List<ArticleDTO> articleDTOs = pageCustom.getContent().stream()
                .map(articleMapper::toDTO)
                .toList();
        return new PageImpl<>(articleDTOs, pageable, pageCustom.getTotalElements());
    }
}
