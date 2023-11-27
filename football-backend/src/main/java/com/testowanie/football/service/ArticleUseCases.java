package com.testowanie.football.service;

import com.testowanie.football.dto.resource.ArticleResource;

public interface ArticleUseCases {
    ArticleResource getArticle(Long id);
}
