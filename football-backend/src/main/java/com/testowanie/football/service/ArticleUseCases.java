package com.testowanie.football.service;

import com.testowanie.football.dto.request.CreateArticleRequest;
import com.testowanie.football.dto.request.CreateCommentRequest;
import com.testowanie.football.dto.request.UpdateArticleRequest;
import com.testowanie.football.dto.resource.ArticleResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleUseCases {

    Page<ArticleResource> getArticles(Pageable pageable);

    ArticleResource getArticle(Long id);

    void createArticle(CreateArticleRequest createArticleRequest);

    void updateArticle(Long id, UpdateArticleRequest updateArticleRequest);

    void deleteArticle(Long id);

    void createComment(Long id, CreateCommentRequest commentRequest);

    void deleteComment(Long id, Long commentId);

    void likeComment(Long id, Long commentId);

    void dislikeComment(Long id, Long commentId);

    void removeLikeFromComment(Long id, Long commentId);

    void removeDislikeFromComment(Long id, Long commentId);

    Page<ArticleResource> searchByQuery(String query, Pageable pageable);
}
