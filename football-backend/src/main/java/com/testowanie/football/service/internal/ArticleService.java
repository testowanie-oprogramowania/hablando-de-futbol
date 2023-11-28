package com.testowanie.football.service.internal;

import com.testowanie.football.dto.request.CreateArticleRequest;
import com.testowanie.football.dto.request.CreateCommentRequest;
import com.testowanie.football.dto.request.UpdateArticleRequest;
import com.testowanie.football.dto.resource.ArticleResource;
import com.testowanie.football.exception.EntityNotFoundException;
import com.testowanie.football.mapper.ArticleMapper;
import com.testowanie.football.mapper.CommentMapper;
import com.testowanie.football.model.Article;
import com.testowanie.football.model.Comment;
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
    private static final String COMMENT_NOT_FOUND = "Comment not found";
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;

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
        final Article article = articleMapper.fromCreateArticleRequest(createArticleRequest);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void updateArticle(Long id, UpdateArticleRequest updateArticleRequest) {
        final Article article = getArticleOrElseThrow(id);
        articleMapper.updateArticleFromUpdateArticleRequest(updateArticleRequest, article);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        final Article article = getArticleOrElseThrow(id);
        articleRepository.delete(article);
    }

    @Override
    @Transactional
    public void createComment(Long id, CreateCommentRequest commentRequest) {
        final Article article = getArticleOrElseThrow(id);

        article.getComments().add(commentMapper.fromCreateCommentRequest(commentRequest));
        articleRepository.save(article);
    }


    @Override
    @Transactional
    public void deleteComment(Long id, Long commentId) {
        final Article article = getArticleOrElseThrow(id);
        final Comment comment = getCommentOrElseThrow(article, commentId);

        article.getComments().remove(comment);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void likeComment(Long id, Long commentId) {
        final Article article = getArticleOrElseThrow(id);
        final Comment comment = getCommentOrElseThrow(article, commentId);

        comment.setThumbsUp(comment.getThumbsUp() + 1);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void dislikeComment(Long id, Long commentId) {
        final Article article = getArticleOrElseThrow(id);
        final Comment comment = getCommentOrElseThrow(article, commentId);

        comment.setThumbsDown(comment.getThumbsDown() + 1);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void removeLikeFromComment(Long id, Long commentId) {
        final Article article = getArticleOrElseThrow(id);
        final Comment comment = getCommentOrElseThrow(article, commentId);

        // normalnie bysmy usuwali like konkretnemu uzytkownikowi i bym nie musial sprawdzac czy nie mniejsze od 0
        if (comment.getThumbsUp() > 0) {
            comment.setThumbsUp(comment.getThumbsUp() - 1);
        }
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void removeDislikeFromComment(Long id, Long commentId) {
        final Article article = getArticleOrElseThrow(id);
        final Comment comment = getCommentOrElseThrow(article, commentId);

        if (comment.getThumbsDown() > 0) {
            comment.setThumbsDown(comment.getThumbsDown() - 1);
        }
        articleRepository.save(article);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResource> searchByQuery(String query, Pageable pageable) {
        return articleRepository.findAllByTitleContainingIgnoreCase(query, pageable)
                .map(articleMapper::toArticleResource);
    }

    private Article getArticleOrElseThrow(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ARTICLE_NOT_FOUND));
    }

    private Comment getCommentOrElseThrow(Article article, Long commentId) {
        return article.getComments().stream().filter(comment -> comment.getId().equals(commentId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));
    }
}
