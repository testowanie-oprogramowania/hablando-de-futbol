package com.testowanie.football.dto.resource;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.List;

@Builder
@Jacksonized
public record ArticleResource(
        Long id,
        String title,
        EditorResource editor,
        Instant createdDate,
        Instant lastModifiedDate,
        String content,
        String image,
        CategoryResource category,
        List<CommentResource> comments
) {
}
