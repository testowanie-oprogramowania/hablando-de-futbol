package com.testowanie.football.mapper;

import com.testowanie.football.dto.resource.ArticleResource;
import com.testowanie.football.model.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleMapper {
    private final EditorMapper editorMapper;
    private final CategoryMapper categoryMapper;
    private final CommentMapper commentMapper;

    public ArticleResource toArticleResource(Article article) {
        return ArticleResource.builder()
                .id(article.getId())
                .title(article.getTitle())
                .editor(editorMapper.toEditorResource(article.getEditor()))
                .publicationDate(article.getPublicationDate())
                .content(article.getContent())
                .image(article.getPhotoUrl())
                .category(categoryMapper.toCategoryResource(article.getCategory()))
                .comments(
                        article.getComments()
                                .stream()
                                .map(commentMapper::toCommentResource)
                                .toList()
                )
                .build();
    }
}
