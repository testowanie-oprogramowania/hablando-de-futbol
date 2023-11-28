package com.testowanie.football.web;

import com.testowanie.football.dto.request.CreateArticleRequest;
import com.testowanie.football.dto.request.CreateCommentRequest;
import com.testowanie.football.dto.request.UpdateArticleRequest;
import com.testowanie.football.dto.resource.ArticleResource;
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
    public ResponseEntity<Void> createComment(@PathVariable Long id, @RequestBody @Valid CreateCommentRequest commentRequest) {
        articleUseCases.createComment(id, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @PathVariable long commentId) {
        articleUseCases.deleteComment(id, commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments/{commentId}/like/add")
    public ResponseEntity<Void> likeComment(@PathVariable Long id, @PathVariable Long commentId) {
        articleUseCases.likeComment(id, commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments/{commentId}/like/remove")
    public ResponseEntity<Void> removeLikeFromComment(@PathVariable Long id, @PathVariable Long commentId) {
        articleUseCases.removeLikeFromComment(id, commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments/{commentId}/dislike/add")
    public ResponseEntity<Void> dislikeComment(@PathVariable Long id, @PathVariable Long commentId) {
        articleUseCases.dislikeComment(id, commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments/{commentId}/dislike/remove")
    public ResponseEntity<Void> removeDislikeFromComment(@PathVariable Long id, @PathVariable Long commentId) {
        articleUseCases.removeDislikeFromComment(id, commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<Page<ArticleResource>> searchArticles(@PathVariable String query, Pageable pageable) {
        final Page<ArticleResource> articles = articleUseCases.searchByQuery(query, pageable);
        return ResponseEntity.ok(articles);
    }
}
