package com.testowanie.football.service.internal;

import com.testowanie.football.dto.request.CreateArticleRequest;
import com.testowanie.football.dto.request.UpdateArticleRequest;
import com.testowanie.football.dto.resource.ArticleResource;
import com.testowanie.football.exception.EntityNotFoundException;
import com.testowanie.football.mapper.ArticleMapper;
import com.testowanie.football.model.Article;
import com.testowanie.football.repository.ArticleRepository;
import com.testowanie.football.service.ArticleUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ArticleService implements ArticleUseCases {
    private static final String ARTICLE_NOT_FOUND = "Article not found";
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResource> getArticles(Pageable pageable) {
        return articleRepository.findAll(pageable).map(articleMapper::toArticleResource);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResource getArticle(Long id) {
        return articleRepository.findById(id).map(articleMapper::toArticleResource)
                .orElseThrow(() -> new EntityNotFoundException(ARTICLE_NOT_FOUND));
    }

    @Override
    @Transactional
    public void createArticle(CreateArticleRequest createArticleRequest) {
        Article article = articleMapper.fromCreateArticleRequest(createArticleRequest);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void updateArticle(Long id, UpdateArticleRequest updateArticleRequest) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ARTICLE_NOT_FOUND));
        articleMapper.updateArticleFromUpdateArticleRequest(updateArticleRequest, article);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ARTICLE_NOT_FOUND));
        articleRepository.delete(article);
    }
}
