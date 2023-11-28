package com.testowanie.football.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        String nickname,
        @NotBlank
        @Size(max = 500)
        String content
) {
}
