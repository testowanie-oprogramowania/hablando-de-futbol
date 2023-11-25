package com.testowanie.football.dto.resource;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Jacksonized
public record CategoryArticlesResource(
        CategoryResource category,
        List<ArticleResource> articles
) {
}
