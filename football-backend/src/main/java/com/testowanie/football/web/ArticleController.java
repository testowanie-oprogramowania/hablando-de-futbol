package com.testowanie.football.web;

import com.testowanie.football.model.Article;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @GetMapping
    public ResponseEntity<Pageable> getArticles(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createArticle(@RequestBody Article article) {
        return ResponseEntity.created(null).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateArticle(@PathVariable long id, @RequestBody Article article) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        return ResponseEntity.noContent().build();
    }
}
