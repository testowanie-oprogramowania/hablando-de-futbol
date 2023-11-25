package com.testowanie.football.dto.resource;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Jacksonized
public record ArticleResource(
        Long id,
        String title,
        EditorResource editor,
        LocalDateTime publicationDate,
        String content,
        String image,
        CategoryResource category,
        List<CommentResource> comments
) {
}
