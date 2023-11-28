package com.testowanie.football.service;

import com.testowanie.football.dto.request.CreateCommentRequest;
import com.testowanie.football.dto.resource.ArticleResource;

public interface ArticleUseCases {
    ArticleResource getArticle(Long id);

    void createComment(Long id, CreateCommentRequest commentRequest);

    void deleteComment(Long id, Long commentId);
}
