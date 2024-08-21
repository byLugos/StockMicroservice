package com.microservicio.stock.infraestructure.config;

import com.microservicio.stock.domain.ports.api.CategoryIn;
import com.microservicio.stock.domain.ports.spi.CategoryOut;
import com.microservicio.stock.domain.service.CategoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.microservicio.stock.infraestructure.jpaout.repos")
public class AppConfig {

    @Bean
    public CategoryIn categoryIn(CategoryOut categoryOut){
        return new CategoryService(categoryOut);
    }
}
