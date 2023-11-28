package com.testowanie.football.service.internal;

import com.testowanie.football.dto.request.CreateCommentRequest;
import com.testowanie.football.dto.resource.ArticleResource;
import com.testowanie.football.exception.EntityNotFoundException;
import com.testowanie.football.mapper.ArticleMapper;
import com.testowanie.football.mapper.CommentMapper;
import com.testowanie.football.model.Article;
import com.testowanie.football.repository.ArticleRepository;
import com.testowanie.football.service.ArticleUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ArticleService implements ArticleUseCases {
    private final String ARTICLE_NOT_FOUND = "Article not found";
    private final String COMMENT_NOT_FOUND = "Comment not found";
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public ArticleResource getArticle(Long id) {
        return articleRepository.findById(id).map(articleMapper::toArticleResource)
                .orElseThrow(() -> new EntityNotFoundException(ARTICLE_NOT_FOUND));
    }

    @Override
    @Transactional
    public void createComment(Long id, CreateCommentRequest commentRequest) {
        final Article article = articleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ARTICLE_NOT_FOUND)
        );

        article.getComments().add(commentMapper.fromCreateCommentRequest(commentRequest));
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void deleteComment(Long id, Long commentId) {
        final Article article = articleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ARTICLE_NOT_FOUND)
        );

        final boolean commentDeleted = article.getComments().removeIf(comment -> comment.getId().equals(commentId));

        if (!commentDeleted) {
            throw new EntityNotFoundException(COMMENT_NOT_FOUND);
        }
    }
}
