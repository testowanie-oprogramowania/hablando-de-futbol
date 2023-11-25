package com.testowanie.football.dto.resource;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CommentResource(
        Long id,
        String nickname,
        String content,
        Integer thumbsUp,
        Integer thumbsDown
) {
}
