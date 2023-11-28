package com.testowanie.football.service;

import com.testowanie.football.dto.request.CreateArticleRequest;
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
}