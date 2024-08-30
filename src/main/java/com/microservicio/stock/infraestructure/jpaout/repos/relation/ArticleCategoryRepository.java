package com.microservicio.stock.infraestructure.jpaout.repos.relation;
import com.microservicio.stock.infraestructure.jpaout.entity.ArticleCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategoryEntity,Long> {
    @Query("SELECT ac.article.id, ac.article.name, ac.article.description, ac.article.quantity, ac.article.price, " +
            "ac.category.id, ac.category.name, ac.article.brand.id, ac.article.brand.name " +  // Añadimos la información de la marca
            "FROM ArticleCategoryEntity ac")
    List<Object[]> findAllArticleDetailsWithCategoriesAndBrand();
}
