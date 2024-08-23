package com.microservicio.stock.infraestructure.config;

import com.microservicio.stock.domain.ports.api.ArticleIn;
import com.microservicio.stock.domain.ports.api.BrandIn;
import com.microservicio.stock.domain.ports.api.CategoryIn;
import com.microservicio.stock.domain.ports.spi.ArticleOut;
import com.microservicio.stock.domain.ports.spi.BrandOut;
import com.microservicio.stock.domain.ports.spi.CategoryOut;
import com.microservicio.stock.domain.service.ArticleService;
import com.microservicio.stock.domain.service.BrandService;
import com.microservicio.stock.domain.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public CategoryIn categoryIn(CategoryOut categoryOut){
        return new CategoryService(categoryOut);
    }

    @Bean
    public BrandIn brandIn(BrandOut brandOut){
        return new BrandService(brandOut);
    }
    @Bean
    public ArticleIn articleIn(ArticleOut articleOut){
        return new ArticleService(articleOut);
    }
}
