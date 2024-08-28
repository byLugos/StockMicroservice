package com.microservicio.stock.infraestructure.controllers;


import com.microservicio.stock.application.dto.ArticleDTO;
import com.microservicio.stock.application.handler.ArticleHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {

    private final ArticleHandler articleHandler;

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        ArticleDTO newArticle = articleHandler.createArticle(articleDTO);
        return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
    }
}
