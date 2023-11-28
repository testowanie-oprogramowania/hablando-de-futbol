package com.testowanie.football.web;

import com.testowanie.football.dto.request.CreateArticleRequest;
import com.testowanie.football.dto.request.CreateCommentRequest;
import com.testowanie.football.dto.request.UpdateArticleRequest;
import com.testowanie.football.dto.resource.ArticleResource;
import com.testowanie.football.model.Article;
import com.testowanie.football.model.Comment;
import com.testowanie.football.service.ArticleUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleUseCases articleUseCases;

    @GetMapping
    public ResponseEntity<Page<ArticleResource>> getArticles(@SortDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(articleUseCases.getArticles(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResource> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleUseCases.getArticle(id));
    }

    @PostMapping
    public ResponseEntity<Void> createArticle(@RequestBody @Valid CreateArticleRequest createArticleRequest) {
        articleUseCases.createArticle(createArticleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateArticle(@PathVariable Long id, @RequestBody @Valid UpdateArticleRequest updateArticleRequest) {
        articleUseCases.updateArticle(id, updateArticleRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleUseCases.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> createComment(@PathVariable long id, @RequestBody CreateCommentRequest commentRequest) {
        articleUseCases.createComment(id, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id, @PathVariable long commentId) {
        articleUseCases.deleteComment(id, commentId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody Comment comment) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<Page<Article>> searchArticles(@PathVariable String query, Pageable pageable) {
        return ResponseEntity.ok().build();
    }
}
