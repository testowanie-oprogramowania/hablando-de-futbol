package com.testowanie.football.service.internal;

import com.testowanie.football.dto.resource.ArticleResource;
import com.testowanie.football.exception.EntityNotFoundException;
import com.testowanie.football.mapper.ArticleMapper;
import com.testowanie.football.repository.ArticleRepository;
import com.testowanie.football.service.ArticleUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ArticleService implements ArticleUseCases {
    private final String ARTICLE_NOT_FOUND = "Article not found";
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    @Transactional(readOnly = true)
    public ArticleResource getArticle(Long id) {
        return articleRepository.findById(id).map(articleMapper::toArticleResource)
                .orElseThrow(() -> new EntityNotFoundException(ARTICLE_NOT_FOUND));
    }
}
