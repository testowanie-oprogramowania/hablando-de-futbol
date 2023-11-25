package com.testowanie.football.web;

import com.testowanie.football.model.Article;
import com.testowanie.football.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @GetMapping
    public ResponseEntity<Page<Article>> getArticles(Pageable pageable) {
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

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> createComment(@PathVariable long id, @RequestBody Comment comment) {
        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id, @PathVariable long commentId) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable long id, @PathVariable long commentId, @RequestBody Comment comment) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<Page<Article>> searchArticles(@PathVariable String query, Pageable pageable) {
        return ResponseEntity.ok().build();
    }
}
