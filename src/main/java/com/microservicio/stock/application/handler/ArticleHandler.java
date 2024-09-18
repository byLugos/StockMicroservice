package com.microservicio.stock.application.handler;
import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.mapper.ArticleMapper;
import com.microservicio.stock.application.mapper.PageMapper;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.ports.api.ArticleIn;
import com.microservicio.stock.domain.pageable.PageCustom;
import com.microservicio.stock.domain.pageable.PageRequestCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
                article.getPrice(),
                articleDTO.getCategories(),
                articleDTO.getBrandId(),
                article.getQuantity()
        );
        return articleMapper.toDTO(newArticle);
    }
    public Page<ArticleDTO> listArticles(PageRequestCustom pageRequestCustom, String name, String sort, List<String> categoryNames, String brandName) {
        PageCustom<Article> pageCustom = articleIn.listArticle(pageRequestCustom, name, sort, categoryNames, brandName);
        return PageMapper.toSpringPage(
                new PageCustom<>(
                        pageCustom.getContent().stream().map(articleMapper::toDTO).toList(),
                        pageCustom.getTotalElements(),
                        pageCustom.getTotalPages(),
                        pageCustom.getCurrentPage(),
                        pageCustom.isAscending()
                )
        );
    }
    public ArticleDTO updateStock(Long articleId, int quantity) {
        Article updatedArticle = articleIn.updateQuantity(articleId, quantity);
        return articleMapper.toDTO(updatedArticle);
    }
    public int currentStock(Long articleId) {
        return articleIn.currentStock(articleId);
    }
}

