package com.testowanie.football.mapper;

import com.testowanie.football.dto.request.CreateArticleRequest;
import com.testowanie.football.dto.request.UpdateArticleRequest;
import com.testowanie.football.dto.resource.ArticleResource;
import com.testowanie.football.model.Article;
import com.testowanie.football.model.Category;
import com.testowanie.football.model.Editor;
import com.testowanie.football.repository.CategoryRepository;
import com.testowanie.football.repository.EditorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ArticleMapper {
    private final EditorMapper editorMapper;
    private final CategoryMapper categoryMapper;
    private final CommentMapper commentMapper;
    private final EditorRepository editorRepository;
    private final CategoryRepository categoryRepository;

    public ArticleResource toArticleResource(Article article) {
        return ArticleResource.builder()
                .id(article.getId())
                .title(article.getTitle())
                .editor(editorMapper.toEditorResource(article.getEditor()))
                .createdDate(article.getCreatedDate())
                .lastModifiedDate(article.getLastModifiedDate())
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

    public Article fromCreateArticleRequest(CreateArticleRequest createArticleRequest) {
        Editor editor = getEditorFromRequest(createArticleRequest.editorId());
        Category category = getCategoryFromRequest(createArticleRequest.categoryName());
        return Article.builder()
                .title(createArticleRequest.title())
                .editor(editor)
                .content(createArticleRequest.content())
                .photoUrl(createArticleRequest.photoUrl())
                .category(category)
                .comments(Set.of())
                .build();
    }

    public void updateArticleFromUpdateArticleRequest(UpdateArticleRequest updateArticleRequest, Article article) {
        Editor editor = getEditorFromRequest(updateArticleRequest.editorId());
        Category category = getCategoryFromRequest(updateArticleRequest.categoryName());
        article.setTitle(updateArticleRequest.title());
        article.setEditor(editor);
        article.setContent(updateArticleRequest.content());
        article.setPhotoUrl(updateArticleRequest.photoUrl());
        article.setCategory(category);
    }

    private Editor getEditorFromRequest(Long editorId) {
        return editorRepository.findById(editorId)
                .orElseThrow(() -> new IllegalArgumentException("Editor not found"));
    }

    private Category getCategoryFromRequest(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }
}
